# Settlein Backend API Documentation

## Base URL
```
http://localhost:8080/api
```

## Authentication
Most APIs require JWT authentication. Include the token in the Authorization header:
```
Authorization: Bearer <your-jwt-token>
```

---

## 1. Authentication APIs (`/api/auth`)

### 1.1 Request OTP for Signup
**POST** `/api/auth/request-otp`

**Request Body:**
```json
{
  "name": "John Doe",
  "email": "john.doe@company.com",
  "password": "password123"
}
```

**Response:**
```json
{
  "message": "OTP sent to email.",
  "id": "550e8400-e29b-41d4-a716-446655440000"
}
```

### 1.2 Verify OTP
**POST** `/api/auth/verify-otp`

**Request Body:**
```json
{
  "email": "john.doe@company.com",
  "otp": "123456",
  "id": "550e8400-e29b-41d4-a716-446655440000"
}
```

**Response:**
```json
{
  "isVerified": true,
  "message": "Otp has been verified, add user details",
  "userId": "550e8400-e29b-41d4-a716-446655440000",
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

### 1.3 Complete Profile
**POST** `/api/auth/complete-profile`
**Headers:** `Authorization: Bearer <token>`

**Request Body:**
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "name": "John Doe",
  "phone": "+1234567890",
  "address": "123 Main St, City, State",
  "profilePic": "https://example.com/profile.jpg",
  "email": "john.doe@company.com",
  "password": "password123",
  "properties_image_urls": [
    "https://example.com/property1.jpg",
    "https://example.com/property2.jpg"
  ]
}
```

**Response:**
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "message": "Profile has been completed successfully"
}
```

### 1.4 Login
**POST** `/api/auth/login`

**Request Body:**
```json
{
  "email": "john.doe@company.com",
  "password": "password123"
}
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

---

## 2. User Management APIs (`/api/user`)

### 2.1 Get Current User Profile
**GET** `/api/user/me`
**Headers:** `Authorization: Bearer <token>`

**Response:**
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "name": "John Doe",
  "email": "john.doe@company.com",
  "phone": "+1234567890",
  "address": "123 Main St, City, State",
  "profilePicUrl": "https://example.com/profile.jpg",
  "company": "company",
  "isVerified": true,
  "fcmToken": "fcm_token_here"
}
```

### 2.2 Update User Profile
**PUT** `/api/user/update`
**Headers:** `Authorization: Bearer <token>`

**Request Body:**
```json
{
  "name": "John Doe Updated",
  "phone": "+1234567890",
  "address": "456 New St, City, State",
  "profilePic": "https://example.com/new-profile.jpg"
}
```

**Response:**
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "name": "John Doe Updated",
  "email": "john.doe@company.com",
  "phone": "+1234567890",
  "address": "456 New St, City, State",
  "profilePicUrl": "https://example.com/new-profile.jpg",
  "company": "company",
  "isVerified": true
}
```

### 2.3 Update FCM Token
**PUT** `/api/user/fcm-token`
**Headers:** `Authorization: Bearer <token>`

**Request Body:**
```json
{
  "fcmToken": "new_fcm_token_here"
}
```

**Response:**
```json
"FCM Token updated"
```

---

## 3. Properties APIs (`/api/properties`)

### 3.1 Create Property
**POST** `/api/properties`
**Headers:** `Authorization: Bearer <token>`
**Content-Type:** `multipart/form-data`

**Form Data:**
- `property` (JSON string):
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "title": "Beautiful 3BHK Apartment",
  "description": "Spacious apartment with modern amenities",
  "location": "Downtown, City",
  "price": 250000.0,
  "imageUrls": [],
  "company": "company",
  "userId": "550e8400-e29b-41d4-a716-446655440000"
}
```
- `images` (files): Multiple image files

**Response:**
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "title": "Beautiful 3BHK Apartment",
  "description": "Spacious apartment with modern amenities",
  "location": "Downtown, City",
  "price": 250000.0,
  "imageUrls": [
    "https://example.com/property1.jpg",
    "https://example.com/property2.jpg"
  ],
  "company": "company",
  "userId": "550e8400-e29b-41d4-a716-446655440000"
}
```

### 3.2 Get All Properties
**GET** `/api/properties?page=0&size=10`
**Headers:** `Authorization: Bearer <token>`

**Response:**
```json
{
  "content": [
    {
      "id": "550e8400-e29b-41d4-a716-446655440000",
      "title": "Beautiful 3BHK Apartment",
      "description": "Spacious apartment with modern amenities",
      "location": "Downtown, City",
      "price": 250000.0,
      "imageUrls": [
        "https://example.com/property1.jpg",
        "https://example.com/property2.jpg"
      ],
      "company": "company",
      "userId": "550e8400-e29b-41d4-a716-446655440000"
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 10
  },
  "totalElements": 1,
  "totalPages": 1
}
```

### 3.3 Get Property by ID
**GET** `/api/properties/{id}`
**Headers:** `Authorization: Bearer <token>`

**Response:**
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "title": "Beautiful 3BHK Apartment",
  "description": "Spacious apartment with modern amenities",
  "location": "Downtown, City",
  "price": 250000.0,
  "imageUrls": [
    "https://example.com/property1.jpg",
    "https://example.com/property2.jpg"
  ],
  "company": "company",
  "userId": "550e8400-e29b-41d4-a716-446655440000"
}
```

### 3.4 Search Properties
**GET** `/api/properties/search?keyword=apartment&location=downtown&minPrice=200000&maxPrice=300000&page=0&size=10`
**Headers:** `Authorization: Bearer <token>`

**Response:**
```json
{
  "content": [
    {
      "id": "550e8400-e29b-41d4-a716-446655440000",
      "title": "Beautiful 3BHK Apartment",
      "description": "Spacious apartment with modern amenities",
      "location": "Downtown, City",
      "price": 250000.0,
      "imageUrls": [
        "https://example.com/property1.jpg",
        "https://example.com/property2.jpg"
      ],
      "company": "company",
      "userId": "550e8400-e29b-41d4-a716-446655440000"
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 10
  },
  "totalElements": 1,
  "totalPages": 1
}
```

---

## 4. Products APIs (`/api/products`)

### 4.1 Create Product
**POST** `/api/products`
**Headers:** `Authorization: Bearer <token>`
**Content-Type:** `multipart/form-data`

**Form Data:**
- `data` (JSON string):
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "title": "iPhone 15 Pro",
  "description": "Latest iPhone with advanced features",
  "location": "Tech Store, City",
  "price": 999.99,
  "category": "Electronics",
  "imageUrls": [],
  "company": "company",
  "userId": "550e8400-e29b-41d4-a716-446655440000"
}
```
- `images` (files): Multiple image files

**Response:**
```json
{
  "id": 1,
  "title": "iPhone 15 Pro",
  "description": "Latest iPhone with advanced features",
  "price": 999.99,
  "category": "Electronics",
  "location": "Tech Store, City",
  "imageUrls": [
    "https://example.com/product1.jpg",
    "https://example.com/product2.jpg"
  ],
  "company": "company",
  "userId": "550e8400-e29b-41d4-a716-446655440000"
}
```

### 4.2 Get All Products
**GET** `/api/products?page=0&size=10`
**Headers:** `Authorization: Bearer <token>`

**Response:**
```json
{
  "content": [
    {
      "id": 1,
      "title": "iPhone 15 Pro",
      "description": "Latest iPhone with advanced features",
      "price": 999.99,
      "category": "Electronics",
      "location": "Tech Store, City",
      "imageUrls": [
        "https://example.com/product1.jpg",
        "https://example.com/product2.jpg"
      ],
      "company": "company",
      "userId": "550e8400-e29b-41d4-a716-446655440000"
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 10
  },
  "totalElements": 1,
  "totalPages": 1
}
```

### 4.3 Get Product by ID
**GET** `/api/products/{id}`

**Response:**
```json
{
  "id": 1,
  "title": "iPhone 15 Pro",
  "description": "Latest iPhone with advanced features",
  "price": 999.99,
  "category": "Electronics",
  "location": "Tech Store, City",
  "imageUrls": [
    "https://example.com/product1.jpg",
    "https://example.com/product2.jpg"
  ],
  "company": "company",
  "userId": "550e8400-e29b-41d4-a716-446655440000"
}
```

### 4.4 Search Products
**GET** `/api/products/search?keyword=iphone&minPrice=500&maxPrice=1500&page=0&size=10`
**Headers:** `Authorization: Bearer <token>`

**Response:**
```json
{
  "content": [
    {
      "id": 1,
      "title": "iPhone 15 Pro",
      "description": "Latest iPhone with advanced features",
      "price": 999.99,
      "category": "Electronics",
      "location": "Tech Store, City",
      "imageUrls": [
        "https://example.com/product1.jpg",
        "https://example.com/product2.jpg"
      ],
      "company": "company",
      "userId": "550e8400-e29b-41d4-a716-446655440000"
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 10
  },
  "totalElements": 1,
  "totalPages": 1
}
```

---

## 5. Feed APIs (`/api/feeds`)

### 5.1 Create Feed
**POST** `/api/feeds`
**Headers:** `Authorization: Bearer <token>`

**Request Body:**
```json
{
  "description": "Looking for a 2BHK apartment in downtown area"
}
```

**Response:**
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "user": {
    "id": "550e8400-e29b-41d4-a716-446655440000",
    "name": "John Doe",
    "email": "john.doe@company.com"
  },
  "description": "Looking for a 2BHK apartment in downtown area",
  "status": "PENDING",
  "createdAt": "2024-01-15T10:30:00",
  "updatedAt": "2024-01-15T10:30:00"
}
```

### 5.2 Get All Feeds
**GET** `/api/feeds`

**Response:**
```json
[
  {
    "id": "550e8400-e29b-41d4-a716-446655440000",
    "user": {
      "id": "550e8400-e29b-41d4-a716-446655440000",
      "name": "John Doe",
      "email": "john.doe@company.com"
    },
    "description": "Looking for a 2BHK apartment in downtown area",
    "status": "PENDING",
    "createdAt": "2024-01-15T10:30:00",
    "updatedAt": "2024-01-15T10:30:00"
  }
]
```

### 5.3 Get Feed by ID
**GET** `/api/feeds/{id}`

**Response:**
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "user": {
    "id": "550e8400-e29b-41d4-a716-446655440000",
    "name": "John Doe",
    "email": "john.doe@company.com"
  },
  "description": "Looking for a 2BHK apartment in downtown area",
  "status": "PENDING",
  "createdAt": "2024-01-15T10:30:00",
  "updatedAt": "2024-01-15T10:30:00"
}
```

### 5.4 Mark Feed as Resolved
**PUT** `/api/feeds/{id}/resolve`
**Headers:** `Authorization: Bearer <token>`

**Response:**
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "user": {
    "id": "550e8400-e29b-41d4-a716-446655440000",
    "name": "John Doe",
    "email": "john.doe@company.com"
  },
  "description": "Looking for a 2BHK apartment in downtown area",
  "status": "RESOLVED",
  "createdAt": "2024-01-15T10:30:00",
  "updatedAt": "2024-01-15T11:00:00"
}
```

---

## 6. Comments APIs (`/api/comments`)

### 6.1 Add Comment
**POST** `/api/comments`
**Headers:** `Authorization: Bearer <token>`

**Request Body:**
```json
{
  "feedId": "550e8400-e29b-41d4-a716-446655440000",
  "comments": "I have a 2BHK apartment available in downtown area. Contact me for details."
}
```

**Response:**
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "feed": {
    "id": "550e8400-e29b-41d4-a716-446655440000",
    "description": "Looking for a 2BHK apartment in downtown area"
  },
  "user": {
    "id": "550e8400-e29b-41d4-a716-446655440000",
    "name": "Jane Smith",
    "email": "jane.smith@company.com"
  },
  "comments": "I have a 2BHK apartment available in downtown area. Contact me for details.",
  "createdAt": "2024-01-15T10:35:00",
  "updatedAt": "2024-01-15T10:35:00"
}
```

### 6.2 Get Comments for Feed
**GET** `/api/comments/feed/{feedId}`

**Response:**
```json
[
  {
    "id": "550e8400-e29b-41d4-a716-446655440000",
    "feed": {
      "id": "550e8400-e29b-41d4-a716-446655440000",
      "description": "Looking for a 2BHK apartment in downtown area"
    },
    "user": {
      "id": "550e8400-e29b-41d4-a716-446655440000",
      "name": "Jane Smith",
      "email": "jane.smith@company.com"
    },
    "comments": "I have a 2BHK apartment available in downtown area. Contact me for details.",
    "createdAt": "2024-01-15T10:35:00",
    "updatedAt": "2024-01-15T10:35:00"
  }
]
```

---

## 7. Chat APIs (`/api/chat`)

### 7.1 Start Chat
**POST** `/api/chat/start`
**Headers:** `Authorization: Bearer <token>`

**Request Body:**
```json
{
  "postId": "550e8400-e29b-41d4-a716-446655440000",
  "postType": "PROPERTY"
}
```

**Response:**
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "postId": "550e8400-e29b-41d4-a716-446655440000",
  "senderId": "550e8400-e29b-41d4-a716-446655440000",
  "receiverId": "550e8400-e29b-41d4-a716-446655440001",
  "postType": "PROPERTY",
  "createdAt": "2024-01-15T10:40:00"
}
```

### 7.2 Send Message
**POST** `/api/chat/send`
**Headers:** `Authorization: Bearer <token>`

**Request Body:**
```json
{
  "chatId": "550e8400-e29b-41d4-a716-446655440000",
  "message": "Hi, I'm interested in your property. Is it still available?"
}
```

**Response:**
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "chatId": "550e8400-e29b-41d4-a716-446655440000",
  "senderId": "550e8400-e29b-41d4-a716-446655440000",
  "message": "Hi, I'm interested in your property. Is it still available?",
  "sentOn": "2024-01-15T10:45:00"
}
```

### 7.3 Get All Chats
**GET** `/api/chat?type=PROPERTY`
**Headers:** `Authorization: Bearer <token>`

**Response:**
```json
[
  {
    "id": "550e8400-e29b-41d4-a716-446655440000",
    "postId": "550e8400-e29b-41d4-a716-446655440000",
    "senderId": "550e8400-e29b-41d4-a716-446655440000",
    "receiverId": "550e8400-e29b-41d4-a716-446655440001",
    "postType": "PROPERTY",
    "createdAt": "2024-01-15T10:40:00"
  }
]
```

### 7.4 Get Chat Messages
**GET** `/api/chat/{chatId}/messages`

**Response:**
```json
[
  {
    "id": "550e8400-e29b-41d4-a716-446655440000",
    "chatId": "550e8400-e29b-41d4-a716-446655440000",
    "senderId": "550e8400-e29b-41d4-a716-446655440000",
    "message": "Hi, I'm interested in your property. Is it still available?",
    "sentOn": "2024-01-15T10:45:00"
  },
  {
    "id": "550e8400-e29b-41d4-a716-446655440001",
    "chatId": "550e8400-e29b-41d4-a716-446655440000",
    "senderId": "550e8400-e29b-41d4-a716-446655440001",
    "message": "Yes, it's still available. When would you like to see it?",
    "sentOn": "2024-01-15T10:50:00"
  }
]
```

---

## 8. Notifications APIs (`/api/notifications`)

### 8.1 Get User Notifications
**GET** `/api/notifications`
**Headers:** `Authorization: Bearer <token>`

**Response:**
```json
[
  {
    "id": "550e8400-e29b-41d4-a716-446655440000",
    "user": {
      "id": "550e8400-e29b-41d4-a716-446655440000",
      "name": "John Doe",
      "email": "john.doe@company.com"
    },
    "message": "New message received from Jane Smith",
    "type": "MESSAGE",
    "read": false,
    "createdAt": "2024-01-15T10:45:00"
  }
]
```

### 8.2 Mark Notification as Read
**PUT** `/api/notifications/{id}/read`
**Headers:** `Authorization: Bearer <token>`

**Response:**
```json
"Marked as read"
```

### 8.3 Mark All Notifications as Read
**PUT** `/api/notifications/read-all`
**Headers:** `Authorization: Bearer <token>`

**Response:**
```json
"All notifications marked as read"
```

---

## Error Responses

### 400 Bad Request
```json
{
  "error": "Invalid OTP"
}
```

### 401 Unauthorized
```json
{
  "error": "Invalid email or password"
}
```

### 403 Forbidden
```json
{
  "error": "Access denied"
}
```

### 404 Not Found
```json
{
  "error": "User not found"
}
```

---

## WebSocket Endpoints

### Chat Message Broadcasting
**WebSocket URL:** `ws://localhost:8080/ws`

**Message Mapping:** `/chat/send`
**Destination:** `/topic/chat/{chatId}`

**Message Format:**
```json
{
  "chatId": "550e8400-e29b-41d4-a716-446655440000",
  "senderId": "550e8400-e29b-41d4-a716-446655440000",
  "message": "Hello!",
  "sentOn": "2024-01-15T10:45:00"
}
```

---

## Test Data Summary

### Sample User Data
```json
{
  "name": "John Doe",
  "email": "john.doe@company.com",
  "phone": "+1234567890",
  "address": "123 Main St, City, State",
  "profilePic": "https://example.com/profile.jpg",
  "password": "password123"
}
```

### Sample Property Data
```json
{
  "title": "Beautiful 3BHK Apartment",
  "description": "Spacious apartment with modern amenities",
  "location": "Downtown, City",
  "price": 250000.0,
  "images": ["property1.jpg", "property2.jpg"]
}
```

### Sample Product Data
```json
{
  "title": "iPhone 15 Pro",
  "description": "Latest iPhone with advanced features",
  "location": "Tech Store, City",
  "price": 999.99,
  "category": "Electronics",
  "images": ["product1.jpg", "product2.jpg"]
}
```

### Sample Feed Data
```json
{
  "description": "Looking for a 2BHK apartment in downtown area"
}
```

### Sample Comment Data
```json
{
  "feedId": "550e8400-e29b-41d4-a716-446655440000",
  "comments": "I have a 2BHK apartment available in downtown area. Contact me for details."
}
```

### Sample Chat Data
```json
{
  "postId": "550e8400-e29b-41d4-a716-446655440000",
  "postType": "PROPERTY",
  "message": "Hi, I'm interested in your property. Is it still available?"
}
```

---

## Notes for Frontend Developer

1. **Authentication Flow:**
   - Request OTP → Verify OTP → Complete Profile → Login
   - Store JWT token and include in all subsequent requests

2. **File Uploads:**
   - Use `multipart/form-data` for image uploads
   - Send JSON data as string in form field
   - Send images as separate files

3. **Pagination:**
   - Use `page` and `size` query parameters
   - Page numbers start from 0

4. **WebSocket:**
   - Connect to WebSocket for real-time chat messages
   - Subscribe to `/topic/chat/{chatId}` for specific chat updates

5. **Error Handling:**
   - Check HTTP status codes
   - Parse error messages from response body

6. **UUIDs:**
   - All IDs are UUIDs (string format)
   - Use UUID format: `550e8400-e29b-41d4-a716-446655440000`

7. **Company-based Data:**
   - Properties and Products are filtered by company
   - Company is extracted from user's email domain

8. **Real-time Features:**
   - Use WebSocket for chat messages
   - Use FCM for push notifications 