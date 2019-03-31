package com.vosievskaya;

import com.vosievskaya.controler.Controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.vosievskaya.Factory.*;

public class MainServlet extends HttpServlet {

    private static final Map<Request, Controller> controllerMap = new ConcurrentHashMap<>();

    static {
        controllerMap.put(Request.of("GET", "/servlet/login"), r ->  ViewModel.of("login"));
        controllerMap.put(Request.of("POST", "/servlet/login"), getLoginUserController(getUserServiceImpl()));
        controllerMap.put(Request.of("GET", "/servlet/categories"), getAllCategoryController(getCategoryService()));
        controllerMap.put(Request.of("GET", "/servlet/category"), getCategoryByIdController(getCategoryService()));
        controllerMap.put(Request.of("GET", "/servlet/product"), getProductByIdController(getProductService()));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Request request = Request.of(req.getMethod(), req.getRequestURI(), req.getParameterMap());
        Controller controller = controllerMap.getOrDefault(request, r -> ViewModel.of("404"));
        ViewModel vm = controller.process(request);
        processViewModel(vm, req, resp);
    }

    private void processViewModel(ViewModel vm, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        vm.getAttributes().forEach(req::setAttribute);
        req.getRequestDispatcher(vm.getRedirectUri()).forward(req, resp);
    }
}
