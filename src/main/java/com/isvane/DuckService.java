package com.isvane;

import com.isvane.dto.DuckTransactionResponse;
import com.isvane.entity.DuckInventory;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class DuckService {

    @ConfigProperty(name = "ducks.number", defaultValue = "100")
    int ducksNum;

    @Transactional
    void onStart(@Observes StartupEvent ev) {
        if (DuckInventory.count() == 0) {
            DuckInventory inventory = new DuckInventory();
            inventory.storeDucks = ducksNum;
            inventory.userDucks = 0;
            inventory.persist();
        }
    }

    private DuckInventory getLockedInventory() {
        return DuckInventory.<DuckInventory>findAll()
            .withLock(LockModeType.PESSIMISTIC_WRITE)
            .firstResult();
    }

    private DuckInventory getReadOnlyInventory() {
        return DuckInventory.<DuckInventory>findAll().firstResult();
    }

    @Transactional
    public DuckTransactionResponse buy(int quantity) {
        DuckInventory inventory = getLockedInventory();

        if (inventory == null) {
            return DuckTransactionResponse.error(
                "Inventory is unavailable!",
                0,
                0
            );
        }

        if (quantity <= 0) {
            return DuckTransactionResponse.error(
                "Quantity must be greater than zero!",
                inventory.userDucks,
                inventory.storeDucks
            );
        }

        if (inventory.storeDucks < quantity) {
            return DuckTransactionResponse.error(
                "Not enough stock",
                inventory.userDucks,
                inventory.storeDucks
            );
        }

        inventory.storeDucks -= quantity;
        inventory.userDucks += quantity;

        return DuckTransactionResponse.ok(
            "Success buying " + quantity + " amount of duck! Happy Quacking!",
            inventory.userDucks,
            inventory.storeDucks
        );
    }

    @Transactional
    public DuckTransactionResponse sell(int quantity) {
        DuckInventory inventory = getLockedInventory();

        if (inventory == null) {
            return DuckTransactionResponse.error(
                "Inventory is unavailable!",
                0,
                0
            );
        }

        if (quantity <= 0) {
            return DuckTransactionResponse.error(
                "Quantity must be greater than zero!",
                inventory.userDucks,
                inventory.storeDucks
            );
        }

        if (inventory.userDucks < quantity) {
            return DuckTransactionResponse.error(
                "Not enough stock",
                inventory.userDucks,
                inventory.storeDucks
            );
        }

        inventory.storeDucks += quantity;
        inventory.userDucks -= quantity;

        return DuckTransactionResponse.ok(
            "Success selling " +
                quantity +
                " amount of duck! don't worry, they are in good hands!",
            inventory.userDucks,
            inventory.storeDucks
        );
    }

    public int getUserDucks() {
        DuckInventory inventory = getReadOnlyInventory();
        return inventory != null ? inventory.userDucks : 0;
    }

    public int getStoreDucks() {
        DuckInventory inventory = getReadOnlyInventory();
        return inventory != null ? inventory.storeDucks : 0;
    }
}
