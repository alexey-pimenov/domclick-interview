#### Build

`./gradlew assemble`

#### Run

`./gradlew bootRun`

#### api base - /api
####Url mappings

* ``/users``

  **GET** - return all users

  **POST** - create new user 

* ``/users/{userId}``

  **GET** - get specified user or 404 if not found

  **PUT** - update existing user

  **DELETE** - delete existing user

* ``/users/{userId}/accounts``

  **GET** - all users's accounts

  **POST** - create new account for user


* ``/users/{userId}/accounts/{accountId}``

   **GET** - get specified account or 404 if not found

   **PUT** - update existing account

   **DELETE** - delete existing account



* ``/users/{userId}/accounts/{accountId}/transactions``

   **GET** - all users's transactions for specified account

   **POST** - submit new money transfer, sample JSON:

  * deposit money on account
    ```json
    {
      "type":"DEPOSIT",
      "amount":100
    }
    ```
  
  * withdraw money from account
    ```json
     {
      "type":"WITHDRAW",
      "amount":100
     }
    ```
    
   * transfer money to another account
     ```json
      {
        "type":"TRANSFER",
        "amount":100,
        "toAccountId":1
      }
     ```

* ``/users/{userId}/accounts/{accountId}/transactions/{transactionId}``

  **GET** - get details of specified transaction