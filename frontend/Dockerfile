FROM node:20-alpine

WORKDIR /app

# Copy package.json and yarn.lock first to leverage Docker cache
COPY package.json .
COPY yarn.lock .

# Install dependencies as root
RUN yarn install

# Copy the rest of the application source code
COPY . .

# Change ownership of the /app directory to the built-in 'node' user
# This user already exists in the base image with UID/GID 1000
RUN chown -R node:node /app

EXPOSE 3000

# Switch to the non-root user for running the application
USER node

CMD ["yarn", "start"]