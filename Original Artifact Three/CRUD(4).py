#python CRUD file
from pymongo import MongoClient
from bson.objectid import ObjectId
import urllib.parse

class AnimalShelter(object):
    """ CRUD operations for Animal collection in MongoDB"""
    
    def __init__(self, username, password): #added username and password to pass to these arguements when calling AnimalShelter class
        #Initializing the MongoClient. This helps to
        #access the MongoDB databases and collection.
        #This is hard-wired to use the aac database, the
        #animals collection, and the aac user.
        #Definitions of the connection string variables are unique to the individual Apporto environment.
        #
        #You must edit the connection variables below to reflect
        #your own instance of MongoDB!
        #Connection Variables
        #
        USER = 'aacuser'
        PASS = 'SNHU1234'
        HOST = 'nv-desktop-services.apporto.com'
        PORT = 32451
        DB = 'aac'
        COL = 'animals'
        #
        # Initialize Connection
        username = urllib.parse.quote_plus('aacuser')
        password = urllib.parse.quote_plus('SNHU1234')
        #
        self.client = MongoClient('mongodb://%s:%s@localhost:32451/?authMechanism=DEFAULT&authSource=AAC' % (USER, PASS))
        self.database = self.client['AAC']
        self.collection = self.database['animals']
        
   # Complete this create method to implement the C in CRUD.
    def create(self, data):
        if data is not None:
            insert = self.database.animals.insert(data) # data should be dictionary
            if insert != 0: #check insert
                return True
            else:
                return False #default return
        else:
            raise Exception("Nothing to save, because data parameter is empty")
   
   # Create method to implement the R in CRUD.
    def read(self, criteria=None):
        if criteria is not None:
            data = self.database.animals.find(criteria,{"_id": False})
            for document in data:
                print(document)
                
        else:
            data = self.database.animals.find({}, {"_id": False})
        return data #return dataset
    
   # Create method to implement the U in CRUD.
    def update(self, searchData, updateData):
        if searchData is not None:
            result = self.database.animals.update_many(searchData, {"$set": updateData })
        else:
            return"{}" #return dataset
        return result.raw_result
    
   # Create method to implement the D in CRUD.
    def delete(self, deleteData):
        if deleteData is not None:
            result = self.database.animals.delete_many(deleteData)
        else:
            return "{}" #return dataset
        return result.raw_result