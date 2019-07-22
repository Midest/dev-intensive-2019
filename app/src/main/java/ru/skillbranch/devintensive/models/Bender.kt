package ru.skillbranch.devintensive.models

class Bender(var status: Status = Status.NORMAL, var question: Question = Question.NAME) {

    fun askQuestion(): String = when(question){
        Question.NAME -> Question.NAME.question
        Question.PROFESSION -> Question.PROFESSION.question
        Question.MATERIAL -> Question.MATERIAL.question
        Question.BDAY -> Question.BDAY.question
        Question.SERIAL -> Question.SERIAL.question
        Question.IDLE -> Question.IDLE.question
    }

    fun listenAnswer(answer: String): Pair<String, Triple<Int, Int, Int>>{
        return when {
            question.validate(answer).not() -> "${question.invalid}\n${question.question}" to status.color
            question == Question.IDLE -> question.question to status.color
            question.answers.contains(answer.toLowerCase()) -> {
                question = question.nextQuestion()
                "Отлично - ты справился\n${question.question}" to status.color
            }
            else -> {
                var done = ""
                if( status == Status.CRITICAL ) {
                    question = Question.NAME
                    done = ". Давай все по новой"
                }
                status = status.nextStatus()
                "Это неправильный ответ$done\n${question.question}" to status.color
            }
        }
    }

    enum class Status(val color: Triple<Int, Int, Int>) {
        NORMAL(Triple(255, 255, 255)) ,
        WARNING(Triple(255, 120, 0)),
        DANGER(Triple(255, 60, 60)),
        CRITICAL(Triple(255, 0, 0));

        fun nextStatus(): Status{
            return if(this.ordinal < values().lastIndex){
                values()[this.ordinal+1]
            } else{
                values()[0]
            }
        }
    }

    enum class Question(val question: String, val invalid: String, val answers: List<String>) {
        NAME("Как меня зовут?", "Имя должно начинаться с заглавной буквы", listOf("бендер", "bender")) {
            override fun nextQuestion(): Question = PROFESSION
            override fun validate(answer: String): Boolean = answer.isNotEmpty() && answer.first().isUpperCase()
        },
        PROFESSION("Назови мою профессию?", "Профессия должна начинаться со строчной буквы", listOf("сгибальщик", "bender")) {
            override fun nextQuestion(): Question = MATERIAL
            override fun validate(answer: String): Boolean = answer.isNotEmpty() && answer.first().isLowerCase()
        },
        MATERIAL("Из чего я сделан?", "Материал не должен содержать цифр", listOf("металл", "дерево", "metal", "iron", "wood")) {
            override fun nextQuestion(): Question = BDAY
            override fun validate(answer: String): Boolean = answer.matches( """\D*""".toRegex())
        },
        BDAY("Когда меня создали?", "Год моего рождения должен содержать только цифры", listOf("2993")) {
            override fun nextQuestion(): Question = SERIAL
            override fun validate(answer: String): Boolean = answer.matches( """\d+""".toRegex())
        },
        SERIAL("Мой серийный номер?", "Серийный номер содержит только цифры, и их 7", listOf("2716057")) {
            override fun nextQuestion(): Question = IDLE
            override fun validate(answer: String): Boolean = answer.matches( """\d{7}""".toRegex())
        },
        IDLE("На этом все, вопросов больше нет", "", listOf()) {
            override fun nextQuestion(): Question = IDLE
            override fun validate(answer: String): Boolean = true
        };

        abstract fun nextQuestion(): Question
        abstract fun validate(answer: String): Boolean
    }

}