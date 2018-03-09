This is the implementation for ATM application.
There are two service classes:
- AccountService
- ATMService

AccountService provides operation for accounts in the bank.
ATMService allows for withdrawing money from the ATM.

AtmLauncher is used to launch the system.

Account'Adm1n' is the admin account. This account should be used to replenish ATM to 2000

The below 3 accounts are present
- Account number 01001, Balance 2738.59
- Account number 01002, Balance 23.00
- Account number 01003, Balance 0.00

The currency notes supported are 50, 20, 10 and 5.
For any given amount, notes with denomination 50 will occupy 20%, notes with denomination 20 will occupy 25%, notes
with denomination 30 will occupy 30% of the amount. The remaining percentage will be filled notes of denomination 5.

Withdrawals are allowed in multiples of 5 only and in the range 5 and 250 inclusive.
The currency supported is GBP only.


