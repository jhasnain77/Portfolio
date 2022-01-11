-- CREATE TABLE STATEMENTS --

-- Users Table --
CREATE TABLE Users (
    UserID INT AUTO_INCREMENT
    ,Username VARCHAR(50) NOT NULL
    ,Password VARCHAR(50) NOT NULL
    ,CreditCardNo VARCHAR(16)
    ,Address VARCHAR(100)
    ,Rating INT DEFAULT 3
    ,TransDone INT DEFAULT 0
    ,PRIMARY KEY (UserID)
    ,CHECK(Rating IN (0, 1, 2, 3, 4, 5))
    ,CHECK(LENGTH(CreditCardNo) = 16)
    ,CHECK(TransDone >= 0)
);

-- Companies Table --
CREATE TABLE Companies (
    CompanyID INT AUTO_INCREMENT
    ,Name VARCHAR(50) NOT NULL
    ,Address VARCHAR(100)
    ,Rating INT DEFAULT 3
    ,PRIMARY KEY (CompanyID)
    ,CHECK(Rating IN (0, 1, 2, 3, 4, 5))
);

-- AvailableTools Table --
CREATE TABLE Tools (
    ToolID INT AUTO_INCREMENT
    ,ToolType VARCHAR(50)
    ,ToolName VARCHAR(50) NOT NULL
    ,UserID INT NOT NULL
    ,CompanyID INT
    ,Price DECIMAL(10, 2) NOT NULL
    ,ForSale BOOLEAN
    ,ForRent BOOLEAN
    ,PRIMARY KEY (ToolID)
    ,FOREIGN KEY (UserID)
        REFERENCES Users(UserID)
        ON DELETE CASCADE
    ,FOREIGN KEY (CompanyID)
        REFERENCES Companies(CompanyID)
        ON DELETE CASCADE
);

-- UnavialableTools Table --
CREATE TABLE UnavailableTools (
    ToolID INT NOT NULL
    ,UserID INT NOT NULL
    ,ReturnDate DATE
    ,FOREIGN KEY (ToolID)
        REFERENCES Tools(ToolID)
        ON DELETE CASCADE
    ,FOREIGN KEY (UserID)
        REFERENCES Users(UserID)
        ON DELETE CASCADE
);

-- FavouriteTools Table --
CREATE TABLE FavouriteTools (
    ToolID INT
    ,UserID INT
    ,FOREIGN KEY (ToolID)
        REFERENCES Tools(ToolID)
        ON DELETE CASCADE
    ,FOREIGN KEY (UserID)
        REFERENCES Users(UserID)
        ON DELETE CASCADE
);

-- UserTransactions Table --
CREATE TABLE UserTransactions (
    ToolID INT
    ,SellerID INT
    ,BuyerID INT
    ,TransactionDate DATE
    ,FOREIGN KEY (ToolID)
        REFERENCES Tools(ToolID)
        ON DELETE CASCADE
    ,FOREIGN KEY (SellerID)
        REFERENCES Users(UserID)
        ON DELETE CASCADE
    ,FOREIGN KEY (BuyerID)
        REFERENCES Users(UserID)
        ON DELETE CASCADE
);

-- CompanyTransaction Table --
CREATE TABLE CompanyTransactions (
    ToolID INT
    ,CompanyID INT
    ,BuyerID INT
    ,TransactionDate DATE
    ,FOREIGN KEY (ToolID)
        REFERENCES Tools(ToolID)
        ON DELETE CASCADE
    ,FOREIGN KEY (CompanyID)
        REFERENCES Companies(CompanyID)
        ON DELETE CASCADE
    ,FOREIGN KEY (BuyerID)
        REFERENCES Users(UserID)
        ON DELETE CASCADE
);
