{
 "cells": [
  {
   "cell_type": "code",
   "execution_count": 5,
   "id": "a188716b-0db1-49dc-b63a-af0a2f11ca3d",
   "metadata": {},
   "outputs": [
    {
     "name": "stdout",
     "output_type": "stream",
     "text": [
      "Requirement already satisfied: pandas in c:\\users\\kirissa\\appdata\\local\\programs\\python\\python313\\lib\\site-packages (2.3.3)\n",
      "Requirement already satisfied: numpy>=1.26.0 in c:\\users\\kirissa\\appdata\\local\\programs\\python\\python313\\lib\\site-packages (from pandas) (2.3.4)\n",
      "Requirement already satisfied: python-dateutil>=2.8.2 in c:\\users\\kirissa\\appdata\\local\\programs\\python\\python313\\lib\\site-packages (from pandas) (2.9.0.post0)\n",
      "Requirement already satisfied: pytz>=2020.1 in c:\\users\\kirissa\\appdata\\local\\programs\\python\\python313\\lib\\site-packages (from pandas) (2025.2)\n",
      "Requirement already satisfied: tzdata>=2022.7 in c:\\users\\kirissa\\appdata\\local\\programs\\python\\python313\\lib\\site-packages (from pandas) (2025.2)\n",
      "Requirement already satisfied: six>=1.5 in c:\\users\\kirissa\\appdata\\local\\programs\\python\\python313\\lib\\site-packages (from python-dateutil>=2.8.2->pandas) (1.17.0)\n"
     ]
    }
   ],
   "source": [
    "!pip install pandas"
   ]
  },
  {
   "cell_type": "code",
   "execution_count": 6,
   "id": "913bbaf0-d7ea-4acd-89e6-d49dfd077c84",
   "metadata": {},
   "outputs": [
    {
     "name": "stdout",
     "output_type": "stream",
     "text": [
      "Collecting pymongo\n",
      "  Downloading pymongo-4.15.3-cp313-cp313-win_amd64.whl.metadata (22 kB)\n",
      "Collecting dnspython<3.0.0,>=1.16.0 (from pymongo)\n",
      "  Downloading dnspython-2.8.0-py3-none-any.whl.metadata (5.7 kB)\n",
      "Downloading pymongo-4.15.3-cp313-cp313-win_amd64.whl (962 kB)\n",
      "   ---------------------------------------- 0.0/962.6 kB ? eta -:--:--\n",
      "   ---------------------------------------- 962.6/962.6 kB 6.7 MB/s  0:00:00\n",
      "Downloading dnspython-2.8.0-py3-none-any.whl (331 kB)\n",
      "Installing collected packages: dnspython, pymongo\n",
      "\n",
      "   ---------------------------------------- 0/2 [dnspython]\n",
      "   ---------------------------------------- 0/2 [dnspython]\n",
      "   ---------------------------------------- 0/2 [dnspython]\n",
      "   ---------------------------------------- 0/2 [dnspython]\n",
      "   ---------------------------------------- 0/2 [dnspython]\n",
      "   ---------------------------------------- 0/2 [dnspython]\n",
      "   ---------------------------------------- 0/2 [dnspython]\n",
      "   -------------------- ------------------- 1/2 [pymongo]\n",
      "   -------------------- ------------------- 1/2 [pymongo]\n",
      "   -------------------- ------------------- 1/2 [pymongo]\n",
      "   -------------------- ------------------- 1/2 [pymongo]\n",
      "   -------------------- ------------------- 1/2 [pymongo]\n",
      "   -------------------- ------------------- 1/2 [pymongo]\n",
      "   -------------------- ------------------- 1/2 [pymongo]\n",
      "   -------------------- ------------------- 1/2 [pymongo]\n",
      "   -------------------- ------------------- 1/2 [pymongo]\n",
      "   -------------------- ------------------- 1/2 [pymongo]\n",
      "   ---------------------------------------- 2/2 [pymongo]\n",
      "\n",
      "Successfully installed dnspython-2.8.0 pymongo-4.15.3\n"
     ]
    }
   ],
   "source": [
    "!pip install pymongo"
   ]
  },
  {
   "cell_type": "code",
   "execution_count": 8,
   "id": "aa976a99-752a-4512-8257-c10565da03ff",
   "metadata": {},
   "outputs": [
    {
     "name": "stdout",
     "output_type": "stream",
     "text": [
      "CSV data successfully loaded into MongoDB.\n"
     ]
    }
   ],
   "source": [
    "import pandas as pd\n",
    "from pymongo import MongoClient\n",
    "\n",
    "# Load your CSV\n",
    "df = pd.read_csv('aac_shelter_outcomes.csv')\n",
    "\n",
    "# Connect to local MongoDB\n",
    "client = MongoClient('mongodb://localhost:27017/')\n",
    "db = client['AAC']  \n",
    "collection = db['animals'] \n",
    "\n",
    "# Insert data\n",
    "data = df.to_dict(orient='records')\n",
    "collection.insert_many(data)\n",
    "\n",
    "print(\"CSV data successfully loaded into MongoDB.\")"
   ]
  }
 ],
 "metadata": {
  "kernelspec": {
   "display_name": "Python 3 (ipykernel)",
   "language": "python",
   "name": "python3"
  },
  "language_info": {
   "codemirror_mode": {
    "name": "ipython",
    "version": 3
   },
   "file_extension": ".py",
   "mimetype": "text/x-python",
   "name": "python",
   "nbconvert_exporter": "python",
   "pygments_lexer": "ipython3",
   "version": "3.13.7"
  }
 },
 "nbformat": 4,
 "nbformat_minor": 5
}
