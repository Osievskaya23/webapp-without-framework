package com.vosievskaya.controller;

import com.vosievskaya.web.Request;
import com.vosievskaya.web.ViewModel;

public interface Controller {

    ViewModel process(Request req);
}
