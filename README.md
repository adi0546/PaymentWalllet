Real-time Wallet Transactions
===============================
## Overview
A real-time balance calculation using Java-Spring services to authorize a transaction and deduct money from wallet or load money into wallet.

## Schema
The [service.yml](service.yml) is the OpenAPI 3.0 schema for the built APIs and objects.

## Details
The service accepts two types of transactions:
1) Loads: Add money to a user walet

2) Authorizations: Conditionally remove money from a user wallet

## Design considerations
I created 3 controllers for 3 API endpoints.
I created UserBalance class to initialise HashMap to store the balance for user.
I am returning error 404 - "User not found" if we try to deduct balance for a user which does not exist.
For every successful put request for a unique messageID, I am storing it in cache in order to keep our API calls idempotent. So that 2+ requests which have same message ID do not increase/decrease amount more than once
I am making sure that API calls are idempotent i.e. the same messageID does not credit into the wallet more than once.

Future Deployment considerations
To deploy this, I would put this application into a docker container and run it as a microservice which could communicate with other services efficiently. 
I would use a message queue to decouple the services and achieve true async calls.
We can further use kubernetes to ensure high availability and scalability of or application.
We can spin up another service to store user data along with balance and inject it in our controllers for efficient communication.