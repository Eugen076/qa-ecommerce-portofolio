# Test Execution Report

## Latest local execution

| Item | Result |
|---|---|
| Date | 2026-11-22 |
| Command | `mvn -B test -Dheadless=true` |
| Environment | Windows 11, Temurin Java 21.0.13, Maven 3.10.0, headless Chrome 157 |
| Total | 11 |
| Passed | 11 |
| Failed | 0 |
| Errors | 0 |
| Skipped | 0 |
| Maven duration | 22.616 seconds |
| Result | **BUILD SUCCESS** |

No test failures occurred in this run. The report summarizes a local run and is not a claim about a GitHub Actions run.

## Requirements and coverage

| Feature | Test case IDs | Existing automated test names |
|---|---|---|
| Login | TC-LOGIN-001–TC-LOGIN-007 | `LoginTest.validLogin`, `LoginTest.invalidLogin`, `LoginTest.lockedOutUser`, `LoginTest.emptyCredentials` |
| Inventory | TC-INVENTORY-001–TC-INVENTORY-008 | `InventoryTest.verifyInventoryPageTitle`, `InventoryTest.removeItemFromCart` |
| Cart | TC-CART-001–TC-CART-005; TC-CHECKOUT-001 (cart item count and product are checked in the end-to-end flow) | `InventoryTest.addSingleItemToCart`, `CheckoutTest.successfulCheckout` |
| Checkout | TC-CHECKOUT-001–TC-CHECKOUT-007 | `CheckoutTest.successfulCheckout`, `CheckoutTest.checkoutRequiresPostalCode`, `CheckoutTest.cancelCheckoutReturnsToCart` |
| Logout | TC-LOGOUT-001–TC-LOGOUT-003 | `LoginTest.logoutTest` |

Only the named methods are automated. Sorting, separate empty-field cases, cart edge cases, and post-logout browser-back behavior remain manual test cases.
