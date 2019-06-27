package com.sysu.ceres.http;

import com.sysu.ceres.model.Message;
import com.sysu.ceres.model.MessageList;
import com.sysu.ceres.model.QuestionList;
import com.sysu.ceres.model.StatisticList;
import com.sysu.ceres.model.Status;
import com.sysu.ceres.model.Survey;
import com.sysu.ceres.model.SurveyFull;
import com.sysu.ceres.model.SurveyList;
import com.sysu.ceres.model.TaskList;
import com.sysu.ceres.model.UserList;

import io.reactivex.Observable;

import io.reactivex.Observer;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    //    用户注册：http://111.230.13.139:8080/ServerAndDB/userRegist?name=ycl&phone=23456789091&email=123@123.com&password=123
    @POST("userRegist")
    Observable<Status> userRigist(@Query("name") String name, @Query("phone") String phone, @Query("email") String email, @Query("password") String password);
    //    用户登录：http://111.230.13.139:8080/ServerAndDB/userLogin?name=ycl&password=123
    @POST("userLogin")
    Observable<Status> userLogin(@Query("name") String name, @Query("password") String password);
    // 查看用户资料：**http://111.230.13.139:8080/ServerAndDB/getUser?name=ycl
    @POST("getUser")
    Observable<Status> getUser(@Query("name") String name);
    //    http://111.230.13.139:8080/ServerAndDB/updateUser?name=Tom&phone=23456789091&email=123@123.com&password=123&money=200
    @POST("updateUser")
    Observable<Status> updateUser(@Query("name") String name, @Query("phone") String phone, @Query("email") String email, @Query("password") String password, @Query("money") int money);


    //    创建任务：http://111.230.13.139:8080/ServerAndDB/createTask?uid=8&title=test&detail=abc&money=3&type=poll&total_num=2&end_time=2020-01-01 00:00:00
    @POST("createTask")
    Observable<Status> createTask(@Query("uid") int uid,
                                  @Query("title") String title,
                                  @Query("detail") String detail,
                                  @Query("money") int money,
                                  @Query("type") String type,
                                  @Query("total_num") int total_num,
                                  @Query("end_time") String end_time);
    //    更新任务：http://111.230.13.139:8080/ServerAndDB/updateTask?tid=3&uid=8&title=test&detail=1234&type=poll&end_time=1577808000000
    @POST("updateTask")
    Observable<Status> updateTask(@Query("tid") int tid,
                                  @Query("uid") int uid,
                                  @Query("title") String title,
                                  @Query("detail") String detail,
                                  @Query("type") String type,
                                  @Query("end_time") String end_time);

//    接受任务：http://111.230.13.139:8080/ServerAndDB/joinTask?tid=4/uid=1
    @POST("joinTask")
    Observable<Status> joinTask(@Query("tid") int tid, @Query("uid") int uid);

//    取消任务：http://111.230.13.139:8080/ServerAndDB/disjoinTask?tid=4&uid=1
    @POST("disjoinTask")
    Observable<Status> disjoinTask(@Query("tid") int tid, @Query("uid") int uid);

//   完成任务： http://111.230.13.139:8080/ServerAndDB/endTask?tid=3&uid=9
    @POST("endTask")
    Observable<Status> endTask(@Query("tid") int tid, @Query("uid") int uid);


//    获取参与任务的用户名单：http://111.230.13.139:8080/ServerAndDB/getJoinUsers?tid=4
    @POST("getJoinUsers")
    Observable<UserList> getJoinUsers(@Query("tid") int tid);

//    http://111.230.13.139:8080/ServerAndDB/getTask?tid=3
    @POST("getTask")
    Observable<TaskList> getTask(@Query("tid") int tid);

    // 显示所有任务：http://111.230.13.139:8080/ServerAndDB/listTasks?extra=false
    @POST("listTasks")
    Observable<TaskList> getTaskList(@Query("extra") boolean extra);

//    按序显示所有任务：
//    http://111.230.13.139:8080/ServerAndDB/listTasksbyDDL?mode=desc&extra=false
//    http://111.230.13.139:8080/ServerAndDB/listTasksbyMoney?mode=desc&extra=false
//    http://111.230.13.139:8080/ServerAndDB/listTasksbyStartTime?mode=desc&extra=false
    @POST("listTasksbyDDL")
    Observable<TaskList> getTaskListByDDL(@Query("mode") String mode, @Query("extra") boolean extra);

    @POST("listTasksbyMoney")
    Observable<TaskList> getTaskListByMoney(@Query("mode") String mode, @Query("extra") boolean extra);

    @POST("listTasksbyStartTime")
    Observable<TaskList> getTaskListByStartTime(@Query("mode") String mode, @Query("extra") boolean extra);




//    获取某个任务下的所有留言：http://111.230.13.139:8080/ServerAndDB/listMessages?tid=3
    @POST("listMessages")
    Observable<MessageList> getMessageList(@Query("tid") int tid);
//    http://111.230.13.139:8080/ServerAndDB/createMessage?tid=9&uid=11&detail=yyy
    @POST("createMessage")
    Observable<Status> createMessage(@Query("tid") int tid, @Query("uid") int uid, @Query("detail") String detail);
//    http://111.230.13.139:8080/ServerAndDB/removeMessage?tid=3&rank=2
    @POST("removeMessage")
    Observable<Status> removeMessage(@Query("tid") int tid, @Query("rank") int floor);

    // 获取该用户参与的所有任务：**http://111.230.13.139:8080/ServerAndDB/getJoinTasks?uid=1
    @POST("getJoinTasks")
    Observable<TaskList> getJoinTasks(@Query("uid") int uid);
    //   getpublishTasks
    @POST("getpublishTasks")
    Observable<TaskList> getpublishTasks(@Query("uid") int uid);

    // 添加调查问卷问题：http://111.230.13.139:8080/ServerAndDB/addQuestion?sid=2&qid=5&qtype=choice&qtitle=where&answer_a=guanghzou&answer_b=shanghai&answer_c=shenzhen&answer_d=others
    @POST("addQuestion")
    Observable<Status> addQuestion(@Query("sid") long sid, @Query("qid") long qid,
                                       @Query("qtype") String qtype, @Query("qtitle") String qtitle,
                                       @Query("answer_a") String answer_a, @Query("answer_b") String answer_b,
                                       @Query("answer_c") String answer_c, @Query("answer_d") String answer_d);

    @POST("createSurvey")
    Observable<Status> createSurvey(@Query("tid") int tid);

    // 获取指定Task下的所有调查问卷  http://111.230.13.139:8080/ServerAndDB/getSurveyList?tid=3
    @POST("getSurveyList")
    Observable<SurveyList> getSurveyList(@Query("tid") long tid);

    // 获取指定调查问卷的所有问题 http://111.230.13.139:8080/ServerAndDB/getSurvey?sid=3
    @POST("getSurvey")
    Observable<SurveyFull> getQuestionList(@Query("sid") long sid);

    // 更新调查问卷选项信息 http://111.230.13.139:8080/ServerAndDB/updateAnswers?sid=2&qid=4&answer=A
    @POST("updateAnswers")
    Observable<Status> updateAnswers(@Query("sid") long sid, @Query("qid") long qid, @Query("answer") String answer);

    // 获取调查问卷统计数据 http://111.230.13.139:8080/ServerAndDB/getAnswers?sid=3
    @POST("getAnswers")
    Observable<StatisticList> getStatisticsList(@Query("sid") long sid);

}
