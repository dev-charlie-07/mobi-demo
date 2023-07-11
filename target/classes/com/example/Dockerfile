# Use the official OpenJDK 17 image as the base image
FROM openjdk:17

# Set the working directory inside the container
WORKDIR /app

# Copy the Java source code into the container
COPY Main.java .

# Compile the Java source code
RUN javac Main.java

# Set the entry point command to run the Java application
CMD ["java", "Main"]

