package com.page27.project.controller;

import com.page27.project.domain.Item;
import com.page27.project.domain.Member;
import com.page27.project.domain.SearchItem;
import com.page27.project.dto.ItemDto;
import com.page27.project.dto.QItemDto;
import com.page27.project.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
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
import java.util.ArrayList;
import java.util.List;


@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemRepository itemRepository;

    @GetMapping("/admin/register")
    public String getRegisterItemPage(){

        return "admin/admin_registerGoods";
    }

//    @GetMapping("/admin/test")
//    public String testPage(){
//        return "admin/admin_uploadTest";
//    }
//
//    @PostMapping("/admin/test")
//    public String requestupload2(MultipartHttpServletRequest mtfRequest) {
//        List<MultipartFile> fileList = mtfRequest.getFiles("file");
//
//        if(mtfRequest.getFiles("file").get(0).getSize() != 0){
//            fileList = mtfRequest.getFiles("file");
//        }
//
//        String src = mtfRequest.getParameter("src");
//        System.out.println("src value : " + src);
//        System.out.println("here test " + fileList.size());
//        String path = "C:\\image\\temp\\";
//
//        for (MultipartFile mf : fileList) {
//            String originFileName = mf.getOriginalFilename(); // 원본 파일 명
//            long fileSize = mf.getSize(); // 파일 사이즈
//
//            System.out.println("originFileName : " + originFileName);
//            System.out.println("fileSize : " + fileSize);
//
//            String safeFile = path + System.currentTimeMillis() + originFileName;
//            try {
//                mf.transferTo(new File(safeFile));
//            } catch (IllegalStateException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
//
//        return "redirect:/";
//    }

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

        List<MultipartFile> fileList = mtfRequest.getFiles("upload_image");
        String src = mtfRequest.getParameter("src");
        System.out.println("File Upload");
        String folderPath = "C:\\image\\temp\\" + firstCategory + "\\" + secondCategory + "\\" + thirdCategory + "\\";
        File folder = new File(folderPath);
        String path = "C:\\image\\temp\\" + firstCategory + "\\" + secondCategory + "\\" + thirdCategory + "\\";

        System.out.println("here test" + fileList.size());
        for(MultipartFile mf : fileList){

            String originFileName = mf.getOriginalFilename();
            long fileSize = mf.getSize();

            System.out.println("origin FileName : " + originFileName);
            System.out.println("file size : " + fileSize);

            //String safeFile = path + System.currentTimeMillis() + originFileName;
            String safeFile = path + System.currentTimeMillis() + originFileName;
//            Item item = new Item(firstCategory,secondCategory,thirdCategory,itemName,itemPrice,itemInfo,itemColor,itemFabric,itemModel,itemSize,itemQuantity,safeFile,saleStatus);
//            itemRepository.save(item);
//            System.out.println("File path : " + safeFile);

            try{
                mf.transferTo(new File(safeFile));
            }catch(IllegalStateException e){
                e.printStackTrace();
            }catch(IOException e){
                e.printStackTrace();
            }


        }// 이미지 파일 업로드
        return "redirect:/";
    }

    @GetMapping("/admin/itemList")
    public String itemListPage(Model model, @PageableDefault(size=3) Pageable pageable, SearchItem searchItem){
        if(searchItem.getItem_name() == null) {
            Page<ItemDto> itemBoards = itemRepository.searchAllItem(pageable);

            int homeStartPage = Math.max(1, itemBoards.getPageable().getPageNumber() - 4);
            int homeEndPage = Math.min(itemBoards.getTotalPages(), itemBoards.getPageable().getPageNumber() + 4);
            model.addAttribute("startPage", homeStartPage);
            model.addAttribute("endPage", homeEndPage);
            model.addAttribute("productList", itemBoards);

            return "admin/admin_Goodslist";
        }
        Page<ItemDto> itemBoards = itemRepository.searchAllItemByCondition(searchItem,pageable);

        int startPage = Math.max(1,itemBoards.getPageable().getPageNumber() - 4);
        int endPage = Math.min(itemBoards.getTotalPages(),itemBoards.getPageable().getPageNumber() + 4);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("productList", itemBoards);
        model.addAttribute("saleStatus", searchItem.getSalestatus());
        model.addAttribute("firstCategory",searchItem.getCmode());
        model.addAttribute("itemName",searchItem.getItem_name());
        return "admin/admin_Goodslist";
    }

    @PatchMapping("/admin/itemList")
    public @ResponseBody String changeStatusPage(@RequestParam(value = "change") List<Long> idList){
        int size = idList.size();

        return null;
    }

    @GetMapping("/admin/orderList")
    public String getOrderPage(){
        return "admin/admin_order";
    }

}
