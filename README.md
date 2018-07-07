# Smart_Cushion
SMART CUSHION as name indicates it is something with a cushion, yes but it has a technical touch to it.  This contain your information about you and your health and they will help you to have a better life.  CUSHION will allow you to view your information like how much time you sit. And from that information our software part will give u better and best ways to be conscious with your health. How it works? Is work in two parts hardware part which help to gain your real time details and software which work on that to give a proper way to handle your health. Basically it will analyse your sitting pattern and provide a simple n effective small exercise which will help to reduce pain of back if exist and for future better health. It also help to reduce illness n provide more energy and that kind of fitness level to do work done efficiently. Arduino code: void loop() { long duration, distance; digitalWrite(trigPin, LOW); // Added this line delayMicroseconds(2); // Added this line digitalWrite(trigPin, HIGH); // delayMicroseconds(1000); - Removed this line delayMicroseconds(10); // Added this line digitalWrite(trigPin, LOW); duration = pulseIn(echoPin, HIGH); distance = (duration/2) / 29.1; if (distance &lt;= 2){ Serial.println(distance); } else{ Serial.println("9"); } delay(500); }
Arduino code:

void loop() {
long duration, distance;
digitalWrite(trigPin, LOW); // Added this line
delayMicroseconds(2); // Added this line
digitalWrite(trigPin, HIGH);
// delayMicroseconds(1000); - Removed this line
delayMicroseconds(10); // Added this line
digitalWrite(trigPin, LOW);
duration = pulseIn(echoPin, HIGH);
distance = (duration/2) / 29.1;
if (distance <= 2){
Serial.println(distance);
}
else{
Serial.println("9");
}
delay(500);
}
