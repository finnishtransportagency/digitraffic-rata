package fi.livi.rata.avoindata.updater.controllers;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import fi.livi.rata.avoindata.updater.service.isuptodate.IsUpToDateService;
import fi.livi.rata.avoindata.updater.service.isuptodate.LastUpdateService;

@Controller
public class LastUpdateController {
    @Autowired
    private IsUpToDateService isUpToDateService;

    @RequestMapping("/last-updated")
    @ResponseBody
    public Map<LastUpdateService.LastUpdatedType, IsUpToDateService.IsToUpToDateDto> getResponse(HttpServletResponse response) {
        response.setHeader("Cache-Control", String.format("max-age=%d, public", 1));

        return isUpToDateService.getIsUpToDates();
    }
}
