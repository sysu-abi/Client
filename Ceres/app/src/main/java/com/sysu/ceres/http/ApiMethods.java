package com.sysu.ceres.http;

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

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ApiMethods {

    /**
     * 封装线程管理和订阅的过程
     */
    public static void ApiSubscribe(Observable observable, Observer observer) {
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 用于获取任务列表
     *
     * @param observer 由调用者传过来的观察者对象
     */
    public static void userRegist(Observer<Status> observer, String name, String phone, String email, String password) {
        ApiSubscribe(Api.getApiService().userRigist(name, phone, email, password), observer);
    }

    public static void updateUser(Observer<Status> observer, String name, String phone, String email, String password, int money) {
        ApiSubscribe(Api.getApiService().updateUser(name, phone, email, password, money), observer);
    }

    public static void userLogin(Observer<Status> observer, String name, String password) {
        ApiSubscribe(Api.getApiService().userLogin(name, password), observer);
    }

    public static void getUser(Observer<Status> observer, String name) {
        ApiSubscribe(Api.getApiService().getUser(name), observer);
    }


    public static void createTask(Observer<Status> observer, int uid,
                                  String title,
                                  String detail,
                                  int money,
                                  String type,
                                  int total_num,
                                  String end_time) {
        ApiSubscribe(Api.getApiService().createTask(uid, title, detail, money, type, total_num, end_time), observer);
    }

    public static void updateTask(Observer<Status> observer, int tid, int uid,
                                  String title,
                                  String detail,
                                  String type,
                                  String end_time) {
        ApiSubscribe(Api.getApiService().updateTask(tid, uid, title, detail, type, end_time), observer);
    }

    public static void joinTask(Observer<Status> observer, int tid, int uid) {
        ApiSubscribe(Api.getApiService().joinTask(tid, uid), observer);
    }

    public static void disjoinTask(Observer<Status> observer, int tid, int uid) {
        ApiSubscribe(Api.getApiService().disjoinTask(tid, uid), observer);
    }

    public static void endTask(Observer<Status> observer, int tid, int uid) {
        ApiSubscribe(Api.getApiService().endTask(tid, uid), observer);
    }

    public static void getTask(Observer<TaskList> observer, int tid) {
        ApiSubscribe(Api.getApiService().getTask(tid), observer);
    }

    public static void getTaskList(Observer<TaskList> observer) {
        ApiSubscribe(Api.getApiService().getTaskList(false), observer);
    }

    public static void getTaskListByDDL(Observer<TaskList> observer, String mode) {
        ApiSubscribe(Api.getApiService().getTaskListByDDL(mode, false), observer);
    }

    public static void getTaskListByMoney(Observer<TaskList> observer, String mode) {
        ApiSubscribe(Api.getApiService().getTaskListByMoney(mode, false), observer);
    }

    public static void getTaskListByStartTime(Observer<TaskList> observer, String mode) {
        ApiSubscribe(Api.getApiService().getTaskListByStartTime(mode, false), observer);
    }

    public static void getJoinUsers(Observer<UserList> observer, int tid) {
        ApiSubscribe(Api.getApiService().getJoinUsers(tid), observer);
    }

    public static void getJoinTasks(Observer<TaskList> observer, int uid) {
        ApiSubscribe(Api.getApiService().getJoinTasks(uid), observer);
    }
    public static void getPublishTasks(Observer<TaskList> observer, int uid) {
        ApiSubscribe(Api.getApiService().getpublishTasks(uid), observer);
    }

    public static void getMessageList(Observer<MessageList> observer, int tid) {
        ApiSubscribe(Api.getApiService().getMessageList(tid), observer);
    }
    public static void createMessage(Observer<Status> observer, int tid, int uid, String detail) {
        ApiSubscribe(Api.getApiService().createMessage(tid, uid, detail), observer);
    }

    public static void removeMessage(Observer<Status> observer, int tid, int floor) {
        ApiSubscribe(Api.getApiService().removeMessage(tid, floor), observer);
    }

    public static void addQuestion(Observer<Status> observer, long sid, long qid, String qtype, String qtitle, String answer_a, String answer_b, String answer_c, String answer_d) {
        ApiSubscribe(Api.getApiService().addQuestion(sid, qid, qtype, qtitle, answer_a, answer_b, answer_c, answer_d), observer);
    }

    public static void createSurvey(Observer<Status> observer, int tid) {
        ApiSubscribe(Api.getApiService().createSurvey(tid), observer);
    }

    public static void getSurveyList(Observer<SurveyList> observer, long tid) {
        ApiSubscribe(Api.getApiService().getSurveyList(tid), observer);
    }

    public static void getQuestionList(Observer<SurveyFull> observer, long sid) {
        ApiSubscribe(Api.getApiService().getQuestionList(sid), observer);
    }

    public static void updateAnswers(Observer<Status> observer, long sid, long qid, String answer) {
        ApiSubscribe(Api.getApiService().updateAnswers(sid, qid, answer), observer);
    }

    public static void getStatisticsList(Observer<StatisticList> observer, long sid) {
        ApiSubscribe(Api.getApiService().getStatisticsList(sid), observer);
    }

}