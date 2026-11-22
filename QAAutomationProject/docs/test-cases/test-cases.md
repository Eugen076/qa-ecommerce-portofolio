# SauceDemo UI Test Cases

Automated names below use `Class.method` and refer only to existing TestNG methods. All other cases are manual and are not represented as automated coverage.

## Login

| ID | Title | Priority | Preconditions | Steps | Test data | Expected result | Automated | Automated test name |
|---|---|---|---|---|---|---|---|---|
| TC-LOGIN-001 | Log in with a valid account | High | Login page is open. | Enter credentials; submit. | `standard_user` / `secret_sauce` | Inventory page opens. | Yes | `LoginTest.validLogin` |
| TC-LOGIN-002 | Reject an incorrect password | High | Login page is open. | Enter a valid username and incorrect password; submit. | `standard_user` / `wrong_password` | Login is rejected with a username/password mismatch message. | Yes | `LoginTest.invalidLogin` |
| TC-LOGIN-003 | Reject a locked-out account | High | Login page is open. | Enter locked-out credentials; submit. | `locked_out_user` / `secret_sauce` | Login is rejected with a locked-out message. | Yes | `LoginTest.lockedOutUser` |
| TC-LOGIN-004 | Validate both credentials left empty | High | Login page is open. | Leave both fields empty; submit. | Blank username and password | Login is rejected and the username-required message is shown. | Yes | `LoginTest.emptyCredentials` |
| TC-LOGIN-005 | Validate an empty username | Medium | Login page is open. | Leave username empty, enter password, and submit. | Blank username / `secret_sauce` | Login is rejected with a username-required message. | No | — |
| TC-LOGIN-006 | Validate an empty password | Medium | Login page is open. | Enter username, leave password empty, and submit. | `standard_user` / blank password | Login is rejected with a password-required message. | No | — |
| TC-LOGIN-007 | Reject an unknown username | Medium | Login page is open. | Enter an unknown username and valid-format password; submit. | `unknown_user` / `secret_sauce` | Login is rejected and no inventory is shown. | No | — |

## Inventory

| ID | Title | Priority | Preconditions | Steps | Test data | Expected result | Automated | Automated test name |
|---|---|---|---|---|---|---|---|---|
| TC-INVENTORY-001 | Verify inventory page title | Medium | Logged in as `standard_user`. | Read the inventory page title. | `standard_user` / `secret_sauce` | Title is `Products`. | Yes | `InventoryTest.verifyInventoryPageTitle` |
| TC-INVENTORY-002 | Inspect a product card | Medium | Logged in on inventory. | Review product name, image, price, and action button. | Any listed product | Product details are associated with the correct product card. | No | — |
| TC-INVENTORY-003 | Remove an inventory product from the cart | Medium | Logged in on inventory. | Add the backpack, then remove it from inventory. | Sauce Labs Backpack | Cart badge is no longer displayed. | Yes | `InventoryTest.removeItemFromCart` |
| TC-INVENTORY-004 | Sort product names ascending | Medium | Logged in on inventory. | Select Name (A to Z); compare displayed names. | Name (A to Z) | Product names appear alphabetically ascending. | No | — |
| TC-INVENTORY-005 | Sort product names descending | Medium | Logged in on inventory. | Select Name (Z to A); compare displayed names. | Name (Z to A) | Product names appear alphabetically descending. | No | — |
| TC-INVENTORY-006 | Sort prices low to high | Medium | Logged in on inventory. | Select Price (low to high); compare displayed prices. | Price (low to high) | Prices are non-decreasing. | No | — |
| TC-INVENTORY-007 | Sort prices high to low | Medium | Logged in on inventory. | Select Price (high to low); compare displayed prices. | Price (high to low) | Prices are non-increasing. | No | — |
| TC-INVENTORY-008 | Open a product detail page | Low | Logged in on inventory. | Open Sauce Labs Backpack by its product name; inspect details. | Sauce Labs Backpack | Detail page identifies the selected product and provides a path back to inventory. | No | — |

## Cart

| ID | Title | Priority | Preconditions | Steps | Test data | Expected result | Automated | Automated test name |
|---|---|---|---|---|---|---|---|---|
| TC-CART-001 | Verify badge count after adding one product | High | Logged in on inventory. | Add Sauce Labs Backpack; inspect the cart badge. | Sauce Labs Backpack | Cart badge shows `1`. | Yes | `InventoryTest.addSingleItemToCart` |
| TC-CART-002 | Remove a product from the cart | High | Cart contains one product. | Click that product's Remove button. | Sauce Labs Backpack | Product is removed and cart item count becomes zero. | No | — |
| TC-CART-003 | View an empty cart | Medium | Cart has no products. | Open the cart page. | Empty cart | No cart item rows are displayed. | No | — |
| TC-CART-004 | Open checkout from an empty cart | Medium | Cart has no products. | Open cart; click Checkout. | Empty cart | Current SauceDemo behavior navigates to checkout step one; no product is present to purchase. | No | — |
| TC-CART-005 | Continue shopping from the cart | Medium | Cart page is open. | Click Continue Shopping. | Any cart state | Inventory page opens. | No | — |

## Checkout

| ID | Title | Priority | Preconditions | Steps | Test data | Expected result | Automated | Automated test name |
|---|---|---|---|---|---|---|---|---|
| TC-CHECKOUT-001 | Complete a valid purchase | High | Logged in; cart contains the backpack. | Open cart; proceed; enter customer details; continue; verify summary; finish. | Taylor Tester / `12345` / Sauce Labs Backpack | Cart count is one; subtotal matches item price; tax is 8% rounded to cents; total is correct; order confirmation is shown. | Yes | `CheckoutTest.successfulCheckout` |
| TC-CHECKOUT-002 | Require a postal code | High | Checkout step one is open with a product in cart. | Enter first and last name; leave postal code blank; continue. | Taylor Tester / blank postal code | Required postal-code error is shown and checkout remains on step one. | Yes | `CheckoutTest.checkoutRequiresPostalCode` |
| TC-CHECKOUT-003 | Cancel checkout back to cart | Medium | Checkout step one is open with a product in cart. | Click Cancel. | Sauce Labs Backpack | Cart page opens and the cart contents remain. | Yes | `CheckoutTest.cancelCheckoutReturnsToCart` |
| TC-CHECKOUT-004 | Require a first name | High | Checkout step one is open. | Leave first name blank; enter last name and postal code; continue. | Blank first name / Tester / `12345` | Checkout remains on step one with a first-name-required validation message. | No | — |
| TC-CHECKOUT-005 | Require a last name | High | Checkout step one is open. | Enter first name; leave last name blank; enter postal code; continue. | Taylor / blank last name / `12345` | Checkout remains on step one with a last-name-required validation message. | No | — |
| TC-CHECKOUT-006 | Reject both names missing | Medium | Checkout step one is open. | Leave first and last name blank; enter postal code; continue. | Blank first and last names / `12345` | Checkout does not advance and indicates the first required field. | No | — |
| TC-CHECKOUT-007 | Calculate a multi-item order summary | Medium | Logged in; inventory has at least two products. | Add two distinct products; proceed through checkout; compare summary values with cart prices. | Backpack and Bike Light; valid customer details | Subtotal equals the sum of item prices; displayed tax and total follow the checkout calculation. | No | — |

## Logout

| ID | Title | Priority | Preconditions | Steps | Test data | Expected result | Automated | Automated test name |
|---|---|---|---|---|---|---|---|---|
| TC-LOGOUT-001 | Log out of an authenticated session | High | Logged in as `standard_user`. | Open the side menu; select Logout. | `standard_user` / `secret_sauce` | Login page is displayed. | Yes | `LoginTest.logoutTest` |
| TC-LOGOUT-002 | Use browser back after logout | High | Logged in as `standard_user`. | Log out; press browser Back. | `standard_user` / `secret_sauce` | Login page remains visible; authenticated inventory is not restored. | No | — |
| TC-LOGOUT-003 | Verify logout option in the side menu | Low | Logged in and inventory is open. | Open the side menu; inspect available menu actions. | `standard_user` / `secret_sauce` | Logout action is visible and can be selected. | No | — |
