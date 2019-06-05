# amount-transfer
RESTful API for amount transfers between accounts.

### Build
> mvn clean install

### Run
Run with default 8080 port:

> java -jar target/money-transfer-1.0.jar

Run with custom port:

> java -Ddw.server.connector.port=9999 -jar target/money-transfer-1.0.jar

## Idea
Application represents a bank, which is preconfigured to be a host (see config.yaml). Lets say "Barklays".

On start it ensures that there is system account which is the only allowed to have unlimited negative balance.

It is possible to create a custom account in host bank "Barklays" and transfer money to it from system account.

Application allows to have only one account with the same name per customer.

Application allows to make Intrabank and Interbank transfers with currency conversion.

Interbank transfer supported only to "Lloyds" bank (for now it logs the action). 
Lets say Barklays has commercial account only in Lloyds and there is no Central Bank.

### Credits
Inspired by Revolut blog (John and Alice in bank transfers)