# Start from official Node image
FROM node:18-alpine

# Set working directory
WORKDIR /app

# Copy files and install dependencies
COPY package*.json ./
RUN npm install

# Copy the rest of the app
COPY . .

# Set the port and run
EXPOSE 3000
CMD ["npm", "start"]
