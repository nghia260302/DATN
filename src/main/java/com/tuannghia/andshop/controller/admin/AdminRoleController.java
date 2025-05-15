package com.tuannghia.andshop.controller.admin;

import com.tuannghia.andshop.controller.common.BaseController;
import com.tuannghia.andshop.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@Controller
@RequestMapping("/admin/roles_management")
public class AdminRoleController extends BaseController {
    private final RoleService roleService;
}

