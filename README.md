# Bank-Outomation
The purpose of this project is to make high level bank automation using a database.

## Entry
In this project, creating a database for a bank's management system and building on this database.
It is aimed that develop an application that performs the necessary operations.

## Problem Definition
There are 3 roles in the bank: customer, representative and bank manager. customers and
required identifying information for employees (Name Surname, Telephone, TR No, Address, E-mail) in the database
should be stored. A customer can have multiple accounts. Accounts registered in the system
can be opened in any currency (TL should come by default). Money between accounts
Currency conversion should be done automatically when necessary. The roles performed
actions are listed below. You can visualize all these actions through a designed interface.
must be displayed.

## Roles In The System
+ Customers
  + They can withdraw and deposit money from their accounts.
  + They can request to open a new account and delete an existing account.
  + They can transfer money between each other.
  + They can update their information. (Address, Telephone etc.)
  + They can transfer money to the bank. (payment of loan debt)
  + They can request a loan from the bank.
  + They can view their monthly summaries. (Sending, withdrawing money made in the current month,
    summary of transactions such as payment of loan debt)
+ Bank Manager
  + It can display the general status of the bank (income, expense, profit and total balance).
  + It can add new currency (Dollar, Euro, Sterling etc.) and update exchange rates.
  + Will be able to determine the salary wages of the employees.
  + It determines the loan and default interest rate.
  + Customer can add.
  + It can advance the system by a month.
  + All transactions (withdrawals, deposits and transfers) realized in the bank
    can view.
  + Whether or not deadlock occurs if the listed processes are started at the same time.
    able to analyze. Deadlock analysis will be explained in a separate section.  
+ Customer Representative
  + Each customer has a representative.
  + Adding, deleting and editing customers (deletion and editing operations are their own
    valid for shopping).
  + They can update customer information. (Address, Telephone etc.)
  + General status of the customers they are interested in (income, expenses and total balance)
    can view.
  + View and approve account opening, deletion and credit requests from customers
    responsibility belongs to the representatives.
  + Transactions of customers they are interested in (withdrawals, deposits and transfers)
    can view.
    
## How To Use
+ You have to use MySQL server
+ You have to import bank.sql 
+ You have to add the jar file to connect java with MySQL.

## Example Piece Of Project Image
![Example](https://github.com/tahapek5454/Bank-Automation/blob/main/image/Temp.png)



     
      
