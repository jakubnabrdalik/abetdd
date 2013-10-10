# ABE TDD


TDD workshop for Agile By Example 2013

## How to prepare


### Setup:

I suggest using gnu/linux/macos, but if you really need windows, that's ok. I'm afraid I won't be able to help though. You may consider VirtualBox with linux.

Apart from that you are going to need:

- jdk 7
- good IDE (I suggest Intellij IDEA, for free from here:  http://confluence.jetbrains.com/display/IDEADEV/IDEA+13+EAP)
- MongoDB (http://docs.mongodb.org/manual/installation/) without any users (no authentication)
- git (obviously)


Also, having gradle >= 1.6 would be nice, but you can go without it, thanks to gradle wrapper. 


## Requirements we're going to work on:

- We need an application for registration to workshops. We have a list of students, and a list of workshops.
- Workshops are either for the evening session or for the morning session. Each student can register to two workshops (one morning and one evening) max.
- Students should be able to see their workshops, and what's left to register.
- Our rooms have limits, so it's important to not go beyond.
- It would be cool if students could also unregister, if they change their mind.
- We don't have passwords or whatever, so just use emails for authentication. No real security needed.
- Also, each teacher should be able to see who's going for their workshops, but grouped by student's role in their company.
- There should be a landing page with list of all workshops.
- Lists of all workshops should be divided to morning and evening slots.

## By the way

The HEAD version of this repo is supposed to be nearly empty, as to not spoil all the fun.

Checkout "full version" commit (9185fc7) for final version.




