

AdsSearchSystem Demo



Please setup the inital code in :

	application.properties
	
	AwsResourceConfig.java
	
Run command : 

	sudo ssh [url] -l ec2-user -i [.pem]

	rm AdsSearchSystem-1.0-SNAPSHOT.jar

	sudo scp -i [.pem] target/AdsSearchSystem-1.0-SNAPSHOT.jar ec2-user@[url]:/home/ec2-user

	java -Dspring.application.json='{"accessKey": "", "secretKey": 	""}' -jar AdsSearchSystem-1.0-SNAPSHOT.jar

	[url]:8080


	screen -ls
	C -a d
	screen -r **

Relevance Score = hitCount / keyWordSize
Quality Score = 0.75 * pClick + 0.25 * Relevance Score
Rank Score = Quality Score * bid


hitCount // the word size user entered

keyWordSize // the key word size of ad

