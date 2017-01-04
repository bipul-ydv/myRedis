package myredis

import com.sun.corba.se.impl.orbutil.ObjectWriter
import grails.converters.JSON
import grails.plugin.redis.Memoize
import grails.plugin.redis.MemoizeDomainObject
import groovy.json.JsonSlurper
import redis.clients.jedis.Jedis


class AnalyticsDataController {
    /*
     *
     *Controller purpose is testing the memoization annotation and redisService
     *
     *
     */

    static defaultAction = "create"
    def getDataService
    def redisService
    def create(){
    }
    def save()  {
        def analytics=new AnalyticsData()
        bindData(analytics,params)
        analytics.save(flush: true)
        render "inserted data"
    }

    def show(){
       /* def data
        redisService.memoize("user":data,expire: 6000) {
        println("Data present ")
            data = getDataService.serviceMethod()
//            print(data + "=====================================")
            println 'cache miss getDomainListWithKeyClass'
            return [analytics: data]
        }*/
       def user
       def importUSer= redisService.memoizeDomainObject(AnalyticsData, "import:user", 600) {
           user = AnalyticsData.list()
           println(user.country)
           println("=====================================miss hit to database")
           return [analytics:user]
        }
        return [analytics:importUSer]
    }

    def form(){
    }

    @Memoize(key="#{analyticsData.country}",expire = "6000")
    def display(AnalyticsData analyticsData){
        println("it's a miss")
//        redisService.sadd()
        def user=AnalyticsData.findByCountry(params.country)
        Integer count=AnalyticsData.count();
        print(user)
        println(count +"is===================================="+AnalyticsData.count())
        return [user:user]
    }


    def myPage(){//if country is not cached it is retrieved from sql table,cached at same time and second request is returned from redis
        if(request.method=="POST"){
        def g=redisService.memoizeDomainObject(AnalyticsData,params.country){
            return  AnalyticsData.findByCountry(params.country)
            }
            return  [analyticsdao: g]
        }
    }

//@MemoizeDomainObject(key="#{analyticsData.country}",expire = "6000")
    def saveInCache(){
        //store domain object after converting it to json and fetch the json and convert it to object
       if(request.method=="POST") {
           Jedis j=new Jedis("localhost")
           def a=new AnalyticsData(params)
           def json = a as JSON
           print(json)
           a.save(flush: true,failOnError: true)
           j.hset("${params.country}","${AnalyticsData}","${json}")
           def valredis=j.hget("${params.country}","${AnalyticsData}")
           def aa=new JsonSlurper().parseText(valredis)
           def myobj= new AnalyticsData(valredis)
           print(">>>>>>>>>>>>>>>>>>>>>>>>>>>"+valredis)
           print("========================"+myobj.country)
       }
    }

    def getdataform(){
    }

    def getData(){
        if(request.method=="POST"){
            def val= redisService.memoizeDomainObject(AnalyticsData,params.country) {
                return AnalyticsData
            }
            print val
            return [val:val]
        }
    }
}

