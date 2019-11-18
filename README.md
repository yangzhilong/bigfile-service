# bigfile-service

This project is based on springboot development and uses components and technologies such as redis, s3, webflux, sentinel and redission.

Technical features:

1. support documents are divided into fragments

2. Execute the second file when the uploaded file is uploaded again.

3. multiple people upload the same file at the same time can be multi-threaded concurrently uploading, speeding up the uploading of large files.

4. Spring webflux replaced Spring mvc

5. Sentinel is used for current limiting

# how to use?
download project, run BigfileServiceApplication's main method or mvn spring-boot:run

Visit the page: http://127.0.0.1:10010/index.html
