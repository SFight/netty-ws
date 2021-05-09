package com.vtears.servlets;

import com.vtears.http.Request;
import com.vtears.http.Response;
import com.vtears.http.Servlet;

/**
 * @author          vtears
 * @date            2021-05-09 17:54:06
 * @description     Servlet处理 此处对业务数据可以进行处理
 */
public class MyServlet extends Servlet {
    @Override
    public void doGet(Request request, Response response) {
        try {
            // 获取 name 参数 并返回
            response.write(request.getParameter("name"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doPost(Request request, Response response) {
        doGet(request, response);
    }
}
