USE wishlistdatabase;

INSERT INTO wishes (WishName, Description, ImgDataPath) values("Body Scrub", "Matas body scrub with seaweed", null), 
("Lotion", "Lotion from Garniere with salicylic acid", null), 
("Antlers", "Goofy antlers that no one finds anything funny about, but the are nostalgic", null), 
("Christmas Sock", "Chistmas sock, to use as a channel for obtaining free sweets during Christmas with norms about this sock as leverage for obtaining free sweets", null),
("Sunscreen", "Because who in the world wants to damage their skin?", null), 
("Parasol", "Complete sunprotection that provides the ultimate sun protection for my skin. It must be in a hexagonically shaped pattern, with red and white stripes", null),
("Skin tonic", "Skin tonic without alchohol for the royal skin", null);

INSERT INTO lists(ListName, UserID) values(
"Christmas", 2),
("Summer", 3), 
("Bathroom", 1);

INSERT INTO listsrelationship (WishID, ListID) values(
1, 3),
(2, 3), 
(3, 1),
(4, 1),
(5, 2),
(6, 2),
(7, 3);

INSERT INTO users(UserName, PasswordHash, Email, CreatedAtTimeof) values(
"MaikenTheMarvel", "Bumblebee1000", "Maiken@gmail.com", CURRENT_TIMESTAMP),
("EmilTheWishMaster", "MischeviousFig", "Emilgurres@gmail.com", CURRENT_TIMESTAMP),
("Gurresø1000", "PraisedBeTommyWiseau", "EmilDagensmandiSkysovs@gmail.com", CURRENT_TIMESTAMP); 


