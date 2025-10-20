# python CRUD file
from pymongo import MongoClient
from bson.objectid import ObjectId
import urllib.parse

class AnimalShelter(object):
    """ CRUD operations for Animal collection in MongoDB """

    # ENHANCEMENT UPDATE: IMPORTED CSV FILE AND SET UP MONGODB ON MY LOCAL COMPUTER VIA POWERSHELL, JUPYTER NOTEBOOK, AND MONGODB COMPASS.
    def __init__(self):
        # Initializing the MongoClient to access the MongoDB databases and collection.
        # This is hard-wired to use the AAC database and the animals collection.
        # Connection variables reflect your local MongoDB instance.
        self.client = MongoClient('mongodb://localhost:27017/')
        self.database = self.client['AAC']         # Matches Compass setup
        self.collection = self.database['animals'] # Matches Compass setup

    # Complete this create method to implement the C in CRUD.
    # def create(self, data):
    #     if data is not None:
    #         insert = self.database.animals.insert(data) # data should be dictionary
    #         if insert != 0: # check insert
    #             return True
    #         else:
    #             return False # default return
    #     else:
    #         raise Exception("Nothing to save, because data parameter is empty")

    def create(self, data):
        if data:
            result = self.collection.insert_one(data)
            return str(result.inserted_id)
        else:
            raise ValueError("No data provided to insert.")

    # Create method to implement the R in CRUD.
    # def read(self, criteria=None):
    #     if criteria is not None:
    #         data = self.database.animals.find(criteria, {"_id": False})
    #         for document in data:
    #             print(document)
    #     else:
    #         data = self.database.animals.find({}, {"_id": False})
    #     return data # return dataset

    # ENHANCEMENT UPDATE: FIX FOR API COMPATIBILITY
    def read(self, criteria=None):
        if criteria:
            return list(self.collection.find(criteria, {"_id": False}))
        else:
            return list(self.collection.find({}, {"_id": False}))

    # Create method to implement the U in CRUD.
    # def update(self, searchData, updateData):
    #     if searchData is not None:
    #         result = self.database.animals.update_many(searchData, {"$set": updateData})
    #     else:
    #         return "{}" # return dataset
    #     return result.raw_result

    # ENHANCEMENT UPDATE: FIX FOR API COMPATIBILITY
    def update(self, searchData, updateData):
        if searchData and updateData:
            result = self.collection.update_many(searchData, {"$set": updateData})
            return result.modified_count
        else:
            return 0

    # Create method to implement the D in CRUD.
    # def delete(self, deleteData):
    #     if deleteData is not None:
    #         result = self.database.animals.delete_many(deleteData)
    #     else:
    #         return "{}" # return dataset
    #     return result.raw_result

    # ENHANCEMENT UPDATE: FIX FOR API COMPATIBILITY
    def delete(self, deleteData):
        if deleteData:
            result = self.collection.delete_many(deleteData)
            return result.deleted_count
        else:
            return 0
