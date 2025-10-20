from flask import Flask, request, jsonify
from CRUD import AnimalShelter

app = Flask(__name__)
shelter = AnimalShelter()

@app.route('/animals', methods=['GET'])
def get_animals():
    query = request.args.to_dict()
    results = shelter.read(query if query else None)
    return jsonify(results), 200

@app.route('/animals', methods=['POST'])
def create_animal():
    data = request.get_json()
    if not data:
        return jsonify({"error": "Missing JSON body"}), 400
    animal_id = shelter.create(data)
    return jsonify({"inserted_id": animal_id}), 201

@app.route('/animals', methods=['PUT'])
def update_animal():
    payload = request.get_json()
    search = payload.get("search")
    update = payload.get("update")
    if not search or not update:
        return jsonify({"error": "Missing search or update data"}), 400
    count = shelter.update(search, update)
    return jsonify({"modified_count": count}), 200

@app.route('/animals', methods=['DELETE'])
def delete_animal():
    data = request.get_json()
    if not data:
        return jsonify({"error": "Missing delete criteria"}), 400
    count = shelter.delete(data)
    return jsonify({"deleted_count": count}), 200

if __name__ == '__main__':
    app.run(debug=True)
