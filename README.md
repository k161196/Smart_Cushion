# Smart Cushion 

SMART CUSHION as name indicates it is something with a cushion, yes but
it has a technical touch to it.

This contain your information about you and your health and they will
help you to have a better life.

CUSHION will allow you to view your information like how much time
you sit. And from that information our software part will give u better and best
ways to be conscious with your health.

How it works?
Is work in two parts hardware part which help to gain your real time details and
software which work on that to give a proper way to handle your health.
Basically it will analyse your sitting pattern and provide a simple n effective
small exercise which will help to reduce pain of back if exist and for future
better health. It also help to reduce illness n provide more energy and that kind
of fitness level to do work done efficiently.

## Need
Sitting is killing you. Numerous studies have pointed to the health risks of
sitting all day, but here you have in one illustration how prolonged sitting affects our
bodies and reminders to interrupt sitting time whenever possible.

The human body simply isn't built to sit all day at a desk or for hour’s verging
out on the couch. Many of us spend more time sitting than sleeping. To avoid the
health risks, we need not just 30 minutes of daily exercise, the info graphic advises,
but taking every opportunity to get up during the day.

Many adults spend more than seven hours a day sitting or lying, and this
typically increases with age to 10 hours or more.
This includes watching TV, using a computer, and reading, doing homework,
travelling by car, bus or train – behaviours referred to as sedentary – but does not
include sleeping.
Experts believe there is something specific about the act of sitting or lying for
too long that is bad for our health.

One of the largest pieces of research to date on the subject – involving almost
800,000 people – found that, compared with those who sat the least, people who sat
the longest had a:

112% increase in risk of diabetes
147% increase in cardiovascular events
90% increase in death caused by cardiovascular events
49% increase in death from any cause

Motivation to this project is that all who work day and night sitting on a place and
day by day they have a major back issue in their life.
Whether tending our crops or hunting wild boar, most of our lives as humans were
lived on our feel. But with the advent of TV, Computers, and the disk jobs, we are
sitting down more than ever before in history.9.3 hours a day, even more than
sleeping 7.7 hours. Our body's weren't built for that
Sitting 6+ hours per day makes you up to 40% likelier to die within 15 years than
someone who sits less Than 3 hours. Even if you exercise

AS SOON AS YOU SIT:
* Enzymes that help break down fat drop 90%
* Calorie burning drops to 1 per minute
* Electrical activity in leg shuts off.

AFTER 2 HOURS:
 Good cholesterol drop 20n %

AFTER 24 HOURS:
 Insulin effectiveness drops 24% and risk of diabetes rises
For many of us, sitting for 8 hours a day at our job is inevitable .But it’s the extra
sitting outside Of work that turns a serious problem deadly. The recommended 30
minutes of activity per day is not enough.

### Arduino code

```

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

```


