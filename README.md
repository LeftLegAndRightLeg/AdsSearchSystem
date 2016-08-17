

TO DO NEXT:

	Front-End 
	
	recommendation algorithm
	
	apache sercurity // used in user system and need to be added later
	
Command:

	mvn clean install
	mvn spring-boot:run
	
	

Relevance Score = hitCount / keyWordSize
Quality Score = 0.75 * pClick + 0.25 * Relevance Score
Rank Score = Quality Score * bid


hitCount // the word size user entered

keyWordSize // the key word size of ad