package myredis

import redis.clients.jedis.Jedis

class DataController {//store and retrieve value from redis cache


    def formData(){ //form to enter country name and population

    }

    def save(){
        if(request.method=="POST") {
            Jedis jedis = new Jedis("localhost");
            jedis.set("${params.country}", "${params.population}");//save the key value in redis cache
            println("inserted data"+params)
            render "data inserted"
        }
    }

    def formShow(){//form to get key(country is key)
    }

    def show(){
        if(request.method=="POST"){
            long millis = System.currentTimeMillis();
            long millis1
            def time1=System.nanoTime()
            def time2
            Jedis jedis=new Jedis("localhost")
            def value=jedis.get("${params.country}")//get value from cache using the key
            println "Value is "+value
            time2=System.nanoTime()
            millis1 = System.currentTimeMillis();
            def diff=millis1-millis
            println millis+"---------"+millis1
            println time2+"---------"+time1
            println("Time diff in nano is"+(time2-time1))
            println("Time diff in millis is"+(millis1-millis))
            render diff+"time taken"+value
        }
    }
}


