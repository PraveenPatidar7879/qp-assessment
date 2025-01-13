# qp-assessment

## Authentication
- **Admin Credentials**:
  - **Username**: `admin`
  - **Password**: `admin123`
  
- **User Credentials**:
  - **Username**: `user`
  - **Password**: `user123`

## Endpoints

### Admin Endpoints (`/admin`)

#### 1. Add a Grocery Item
- **Endpoint**: `POST /admin/add`
- **Request Body**: JSON with `name`, `inventory`, and `price`.

#### 2. Get All Grocery Items
- **Endpoint**: `GET /admin/items`

#### 3. Delete a Grocery Item
- **Endpoint**: `DELETE /admin/delete/{id}`

#### 4. Update a Grocery Item
- **Endpoint**: `PUT /admin/update/{id}`
- **Request Body**: JSON with updated `name`, `inventory`, and `price`.

#### 5. View Inventory Levels
- **Endpoint**: `GET /admin/inventory`

#### 6. Update Inventory Level
- **Endpoint**: `PUT /admin/inventory/{id}`
- **Request Parameters**: `inventory` (new inventory level)

---

### User Endpoints (`/user`)

#### 1. Get All Grocery Items
- **Endpoint**: `GET /user/items`

#### 2. Place an Order
- **Endpoint**: `POST /user/order`
- **Request Body**: JSON with `orderItems` (grocery item IDs and quantities).

---

## Error Handling

- **Out of Stock**: If an item is out of stock while placing an order, you will receive a `400 Bad Request` with the following message:
  ```json
  {
    "error": "Out of Stock",
    "message": "Item not available in required quantity"
  }




To run the application in a Docker container, follow these steps:

1. Build the Project with Maven
                           Run the following command to build the project

                                 mvn clean package
3. Build the Docker Image

                            docker build -t backend-app .
   
4. Run the Docker container, binding it to the desired port:

                           docker run -d -p 8989:8989 backend-app
