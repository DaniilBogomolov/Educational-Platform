FROM tomcat:9.0-jdk14-openjdk-oracle
COPY build/libs/*.war /usr/local/tomcat/webapps/.war
EXPOSE 8080
CMD ["catalina.sh", "run"]