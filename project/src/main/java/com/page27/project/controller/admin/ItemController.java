package com.page27.project.controller.admin;

import com.page27.project.domain.Item;
import com.page27.project.domain.SearchItem;
import com.page27.project.dto.ItemDto;
import com.page27.project.repository.ItemRepository;
import com.page27.project.service.ItemServiceImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;


@Controller
@RequiredArgsConstructor
public class ItemController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    private final ItemRepository itemRepository;
    private final ItemServiceImpl itemServiceImpl;

    @GetMapping("/admin/register")
    public String getRegisterItemPage(){
        return "admin/admin_registerGoods";
    }

    @PostMapping("/admin/register")
    public String requestupload2(MultipartHttpServletRequest mtfRequest,@RequestParam("cmode1")String firstCategory
                                 ,@RequestParam("cmode2")String secondCategory
                                 ,@RequestParam("cmode3")String thirdCategory
                                 ,@RequestParam("item_name")String itemName
                                 ,@RequestParam("price")int itemPrice
                                 ,@RequestParam("salestatus")String saleStatus
                                 ,@RequestParam("info")String itemInfo
                                 ,@RequestParam("color")String itemColor
                                 ,@RequestParam("size")String itemSize
                                 ,@RequestParam("stock_quantity")int itemQuantity
                                 ,@RequestParam("fabric")String itemFabric
                                 ,@RequestParam("model")String itemModel
    ){

        String folderPath = "C:\\Users\\skyey\\Desktop\\페이지27 프로젝트\\프로젝트\\project\\page27clone\\project\\src\\main\\resources\\static\\image\\Item\\" + firstCategory + "\\" + secondCategory + "\\" + itemName + "\\";

        File newFile = new File(folderPath);
        if(newFile.mkdirs()){
            logger.info("directory make ok");
        }else{
            logger.warn("directory can't make");
        }

        List<MultipartFile> fileList = mtfRequest.getFiles("upload_image");
        Long newItemIdx = itemRepository.searchMaxItemIdx();

        for(int i = 0; i< fileList.size();i++){
            String originFileName = fileList.get(i).getOriginalFilename();
            String safeFile = folderPath + originFileName;

            Item item = new Item(firstCategory,secondCategory,thirdCategory,itemName,itemPrice,itemInfo,itemColor,itemFabric,itemModel,itemSize,itemQuantity,safeFile,saleStatus,newItemIdx + 1,true);

            if(i == 0){
                item.setRep(true);
            }
            else{
                item.setRep(false);
            }

            itemRepository.save(item);

            try{
                fileList.get(i).transferTo(new File(safeFile));
            }catch(IllegalStateException e){
                e.printStackTrace();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        return "redirect:/admin/itemList";
    }

    @GetMapping("/admin/itemList")
    public String itemListPage(Model model, @PageableDefault(size=5) Pageable pageable, SearchItem searchItem){
        int homeEndPage= 0;
        int homeStartPage = 0;
        if(searchItem.getItem_name() == null) {
            Page<ItemDto> itemBoards = itemRepository.searchAllItem(pageable);

            homeStartPage = Math.max(1, itemBoards.getPageable().getPageNumber());
            homeEndPage = Math.min(itemBoards.getTotalPages(), itemBoards.getPageable().getPageNumber() + 5);
            System.out.println("이게 현재?" + itemBoards.getPageable());
            System.out.println("start page : " + homeStartPage);
            System.out.println(homeEndPage);
            model.addAttribute("startPage", homeStartPage);
            model.addAttribute("endPage", homeEndPage);
            model.addAttribute("productList", itemBoards);

            return "admin/admin_Goodslist";
        }
        Page<ItemDto> itemBoards = itemRepository.searchAllItemByCondition(searchItem,pageable);

        int startPage = Math.max(1,itemBoards.getPageable().getPageNumber());
        int endPage = startPage + 4;
        System.out.println("start page : " + startPage);
        System.out.println(endPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("productList", itemBoards);
        model.addAttribute("saleStatus", searchItem.getSalestatus());
        model.addAttribute("firstCategory",searchItem.getCmode());
        model.addAttribute("itemName",searchItem.getItem_name());
        return "admin/admin_Goodslist";
    }

    @ResponseBody
    @PatchMapping("/admin/itemList1")
    public String itemStatusOnSalePage(@RequestBody List<Map<String,String>> allData){
        for(Map<String,String> temp : allData){
            itemServiceImpl.changeItemStatusOnSale(temp.get("itemIdx"),temp.get("itemColor"));
        }
        return "상품 상태 판매 변경완료";
    }

    @ResponseBody
    @PatchMapping("/admin/itemList2")
    public String itemStatusSoldOutPage(@RequestBody List<Map<String,String>> allData ){

        for(Map<String,String> temp : allData){
            itemServiceImpl.changeItemStatusSoldOut(temp.get("itemIdx"),temp.get("itemColor"));
        }
        return "상품 상태 품절 변경완료";
    }

    @ResponseBody
    @DeleteMapping("/admin/itemList3")
    public String itemdeletePage(@RequestBody List<Map<String,String>> allData){
        for(Map<String,String> temp : allData){
            itemServiceImpl.deleteItemById(temp.get("itemIdx"),temp.get("itemColor"));
        }
        return "상품 삭제 완료";
    }


}
