package fastcampus.mongodb

import com.mongodb.reactivestreams.client.MongoClient
import jakarta.annotation.PreDestroy
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MongoApplication

fun main(args: Array<String>) {

    runApplication<MongoApplication>(*args)
}
