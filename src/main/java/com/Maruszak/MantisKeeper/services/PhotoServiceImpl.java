package com.Maruszak.MantisKeeper.services;

import com.Maruszak.MantisKeeper.model.Invertebrate;
import com.Maruszak.MantisKeeper.model.Photo;
import com.Maruszak.MantisKeeper.repository.PhotoRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Service
public class PhotoServiceImpl {


    @Autowired
    private PhotoRepository photoRepository;

    @Transactional
    public void saveFiles(List<MultipartFile> files, Invertebrate invert) {
        if (files != null) {
            Cloudinary cloudinary = new Cloudinary();
            for (MultipartFile multipartFile : files) {
                try {
                    String file = "data:" + multipartFile.getContentType() + ";base64," +
                            DatatypeConverter.printBase64Binary(multipartFile.getBytes());
                    Map uploadResults = cloudinary.uploader().upload(file, ObjectUtils.asMap(
                            "cloud_name", "mmaruszak",
                            "api_key", "816365741111569",
                            "api_secret", "i8K4Lh4avhtJXCTLA56jZSlJtFA",
                            "upload_preset", "xtmxos3s"
                    ));
                    Photo Photo = new Photo(uploadResults.get("url").toString(),
                            uploadResults.get("public_id").toString(), LocalDateTime.now(), invert);
                    photoRepository.save(Photo);
                } catch (IOException e) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There was a problem with files upload");
                }
            }
        }
    }

    List<Photo> findAllByInvert(Invertebrate invert) {
        return photoRepository.findAllByInvertebrateOrderByAddedAsc(invert);
    }

    @Transactional
    public void deleteAllByInvert(Invertebrate invert) {
        List<Photo> photos = findAllByInvert(invert);
        deletePhotos(photos);
        photoRepository.deleteAllByInvertebrate(invert);
    }

    @Transactional
    public void deleteAllById(List<UUID> ids) {
        List<Photo> photos = photoRepository.findByIdIn(ids);
        deletePhotos(photos);
        photoRepository.deleteByIdIn(ids);
    }

    private void deletePhotos(List<Photo> photoGalleries) {
        Cloudinary cloudinary = new Cloudinary();
        for (Photo photo : photoGalleries) {
            try {
                cloudinary.uploader().destroy(photo.getPublicID(),
                        ObjectUtils.asMap(
                                "cloud_name", "mmaruszak",
                                "api_key", "816365741111569",
                                "api_secret", "i8K4Lh4avhtJXCTLA56jZSlJtFA"
                        ));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
