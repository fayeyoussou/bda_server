package sn.youdev.services;

import org.springframework.web.multipart.MultipartFile;
import sn.youdev.config.error.EntityNotFoundException;
import sn.youdev.config.error.EntreeException;
import sn.youdev.config.error.UserNotFoundException;
import sn.youdev.dto.request.ArticleEditRequest;
import sn.youdev.dto.request.ArticleRequest;
import sn.youdev.dto.request.SectionRequest;
import sn.youdev.dto.response.ArticleResponse;
import sn.youdev.dto.response.ArticleResponseList;
import sn.youdev.dto.response.SectionResponse;
import sn.youdev.dto.response.SectionString;
import sn.youdev.model.Article;
import sn.youdev.model.Section;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

public interface ArticleService {
    ArticleResponse addArticle(ArticleRequest request, HttpServletRequest htRequest) throws UserNotFoundException, IOException;
    List<ArticleResponseList> listArticle();
    ArticleResponse getArticleById(Long id) throws EntityNotFoundException, EntreeException;
    Article getArticle(Long id) throws EntityNotFoundException;
    Section getSection(Long article_id,Byte position) throws EntityNotFoundException;
    ArticleResponse editArticle(Long id, ArticleEditRequest request, HttpServletRequest httpServletRequest) throws EntityNotFoundException;
    ArticleResponse addOrEditSection(Long id, SectionString sectionString) throws EntityNotFoundException;
    Boolean deleteArticle(Long id) throws EntityNotFoundException;
    ArticleResponse changeImage(Long id, MultipartFile image) throws EntityNotFoundException, IOException;
    ArticleResponse deleteSection(Long id,Byte position) throws EntityNotFoundException;
}
