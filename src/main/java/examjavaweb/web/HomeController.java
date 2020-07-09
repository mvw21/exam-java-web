package examjavaweb.web;

import examjavaweb.model.view.ProductViewModel;
import examjavaweb.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    private final ProductService productService;
    private final ModelMapper modelMapper;


    public HomeController(ProductService productService, ModelMapper modelMapper) {
        this.productService = productService;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/")
    public String index(HttpSession httpSession, Model model){
        if(httpSession.getAttribute("user") == null){
            return "index";
        }
//            modelAndView.addObject("products",this.productService.findAllItems());
//            modelAndView.addObject("totalSum",this.productService.getAllPrice());

        // If user is logged in show all items

        // Getting all lists of items
        List<ProductViewModel> food = this.productService.findAllByCategory("Food")
                .stream().map(product -> {
                    ProductViewModel productViewModel = this.modelMapper.map(product, ProductViewModel.class);
                    productViewModel.setImgUrl(String.format("/img/%s.png", product.getCategory().getCategoryName().name().toLowerCase()));
                    return productViewModel;
                })
                .collect(Collectors.toList());
        List<ProductViewModel> drink = this.productService.findAllByCategory("Drink")
                .stream().map(product -> {
                    ProductViewModel productViewModel = this.modelMapper.map(product, ProductViewModel.class);
                    productViewModel.setImgUrl(String.format("/img/%s.png", product.getCategory().getCategoryName().name().toLowerCase()));
                    return productViewModel;
                })
                .collect(Collectors.toList());

        List<ProductViewModel> household = this.productService.findAllByCategory("Household")
                .stream().map(product -> {
                    ProductViewModel productViewModel = this.modelMapper.map(product, ProductViewModel.class);
                    productViewModel.setImgUrl(String.format("/img/%s.png", product.getCategory().getCategoryName().name().toLowerCase()));
                    return productViewModel;
                })
                .collect(Collectors.toList());

        List<ProductViewModel> other = this.productService.findAllByCategory("Other")
                .stream().map(product -> {
                    ProductViewModel productViewModel = this.modelMapper.map(product, ProductViewModel.class);
                    productViewModel.setImgUrl(String.format("/img/%s.png", product.getCategory().getCategoryName().name().toLowerCase()));
                    return productViewModel;
                })
                .collect(Collectors.toList());

        // Adding all lists of items to the model
        model.addAttribute("food", food);
        model.addAttribute("drink", drink);
        model.addAttribute("household", household);
        model.addAttribute("other", other);
        model.addAttribute("totalPrice", this.productService.getAllPrice());
        return "home";

    }
}
