package com.pondmanage.controller;

import com.pondmanage.model.Pond;
import com.pondmanage.model.WareHouse;
import com.pondmanage.model.Zone;
import com.pondmanage.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class MainController {

    @Autowired
    ZoneService zoneService;

    @Autowired
    ShrimpService shrimpService;

    @Autowired
    ProductService productService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    PondService pondService;

    @Autowired
    DietService dietService;

    @Autowired
    WareHouseService wareHouseService;

    @Autowired
    EnterHistoryService enterHistoryService;

    @GetMapping(value = {"/zones", "/"})
    public String zones(Model model) {
        model.addAttribute("zones", zoneService.findAll());
        return "zones/zones";
    }

    @GetMapping("/zones/id={id}/ponds")
    public String ponds(@PathVariable Long id, Model model) {
        Zone zone = zoneService.getById(id);
        if (zone == null) {
            return "redirect:/zones";
        }
        model.addAttribute("zone", zone);
        model.addAttribute("ponds", pondService.findAll(zone));
        return "ponds/ponds";
    }

    @GetMapping("/zones/id={id}/diets")
    public String diets(@PathVariable Long id, Model model) {
        Zone zone = zoneService.getById(id);
        if (zone == null) {
            return "redirect:/zones";
        }
        model.addAttribute("zone", zone);
        model.addAttribute("diets", dietService.findAll(zone));
        return "diets/diets";
    }

    @GetMapping("/pond/id={id}/product_spends")
    public String useProduct(@PathVariable Long id, Model model) {
        Pond pond = pondService.getById(id);
        if (pond == null) {
            return "redirect:/zones";
        }
        model.addAttribute("pond", pond);
        //model.addAttribute("diets",dietService.findAll(zone));
        return "ponds/pond_product_spends";
    }

    @GetMapping("/pond/id={id}/other_spends")
    public String useOther(@PathVariable Long id, Model model) {
        Pond pond = pondService.getById(id);
        if (pond == null) {
            return "redirect:/zones";
        }
        model.addAttribute("pond", pond);
        //model.addAttribute("diets",dietService.findAll(zone));
        return "ponds/pond_other_spends";
    }

    @GetMapping("/pond/id={id}/environment")
    public String environment(@PathVariable Long id, Model model) {
        Pond pond = pondService.getById(id);
        if (pond == null) {
            return "redirect:/zones";
        }
        model.addAttribute("pond", pond);
        //model.addAttribute("diets",dietService.findAll(zone));
        return "ponds/pond_environment";
    }

    /**
     * Shrimp page controller
     *
     * @return
     */
    @GetMapping("/shrimps")
    public String shrimps(Model model) {
        model.addAttribute("shrimps", shrimpService.findAll());
        return "shrimps/shrimps";
    }

    /**
     * Employees page controller
     *
     * @return
     */
    @GetMapping("/employees")
    public String employees(Model model) {
        model.addAttribute("employees", employeeService.findAll());
        return "employees/employees";
    }

    /**
     * Warehouse page controller
     *
     * @return
     */
    @GetMapping("/warehouse")
    public String warehouse(Model model) {
        model.addAttribute("warehouse", wareHouseService.findAll());
        return "warehouse/warehouse";
    }

    @GetMapping("/warehouse/id={id}/enter_histories")
    public String enterHistories(@PathVariable Long id, Model model) {
        WareHouse wareHouse = wareHouseService.getById(id);
        if (wareHouse == null) {
            return "redirect:/warehouse";
        }
        model.addAttribute("warehouse", wareHouse);
        model.addAttribute("enterHistories", enterHistoryService.findByWareHouse(id));
        return "enterhistories/enter_histories";
    }

    /**
     * Products page controller
     *
     * @return
     */
    @GetMapping("/products")
    public String products(Model model) {
        model.addAttribute("products", productService.findAll());
        return "products/products";
    }

    /**
     * Statistics page controller
     *
     * @return
     */
    @GetMapping("/statistics")
    public String statistics() {
        return "statistics/statistics";
    }
}

