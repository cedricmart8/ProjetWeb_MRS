# Projet Web MRS
## MARTIN Cédric & DUBERNET Samuel

### * Lancement de mongodb :
sudo su  
mongod --smallfiles --port 8081  

### Lancement de l'API
sudo su  
cd projet/ProjetWeb_MRS/projetMRS/  
mvn clean install  
java -jar target/projetMRS-0.0.1-SNAPSHOT-jar-with-dependencies.jar  

#### Lancement de l'Application MSR
sudo su  
cd projet/ProjetWEB_MRS_Appli  
npm start  
