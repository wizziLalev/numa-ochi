FROM eclipse-temurin:21-jdk-jammy

# Define build arguments for UID and GID, defaulting to 1000
ARG UID=1000
ARG GID=1000

WORKDIR /app

# Copy Gradle wrapper and build files
COPY gradle gradle
COPY gradlew .
COPY build.gradle.kts .
COPY settings.gradle.kts .

# Copy source code
COPY src src

# Build the application (this step runs as root during image build)
RUN ./gradlew bootJar

# Create a non-root user with the host's UID/GID
# Use '|| true' to prevent build failure if group/user already exists (e.g., in some base images)
RUN addgroup --gid $GID appgroup || true && \
    adduser --uid $UID --ingroup appgroup --shell /bin/sh --disabled-password appuser || true

# Change ownership of the /app directory to the new user
# This is crucial so the non-root user can access files within the mounted volume
RUN chown -R appuser:appgroup /app

EXPOSE 8080

# Switch to the non-root user for running the application
USER appuser

# Use the shell form of ENTRYPOINT to allow wildcard expansion
# Or, more robustly, use /bin/sh -c to find the JAR file
ENTRYPOINT ["/bin/sh", "-c", "java -jar $(find build/libs -name '*.jar')"]
