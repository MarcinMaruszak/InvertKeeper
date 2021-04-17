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
import java.util.*;

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
                            "upload_preset", "xtmxos3s",
                            "folder", "inverts/" + invert.getUser().getId().toString()
                    ));
                    Photo Photo = new Photo(uploadResults.get("url").toString(),
                            uploadResults.get("public_id").toString(), LocalDateTime.now(), invert, false);
                    photoRepository.save(Photo);
                } catch (IOException e) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "{\"message\":\"There was a problem with files upload\"}");
                }
            }
        }
    }


    public List<Photo> findAllByInvert(Invertebrate invert) {
        return photoRepository.findAllByInvertebrateOrderByAddedAsc(invert);
    }

    public List<Photo> findAllFromGalleryByInvert(Invertebrate invert) {
        return photoRepository.findAllByInvertebrateAndAvatarFalseOrderByAddedAsc(invert);
    }

    public Photo findAvatar(Invertebrate invertebrate) {
        return photoRepository.findByInvertebrateAndAvatarTrue(invertebrate).orElse(null);
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

    @Transactional
    public void saveAsAvatar(UUID avatarId, Invertebrate invert) {
        Optional<Photo> currentAvatarOpt = photoRepository.findByInvertebrateAndAvatarTrue(invert);
        if (currentAvatarOpt.isPresent()) {
            Photo avatar = currentAvatarOpt.get();
            avatar.setAvatar(false);
            photoRepository.save(avatar);
        }
        Optional<Photo> photoOptional = photoRepository.findById(avatarId);
        if (photoOptional.isPresent()) {
            Photo photo = photoOptional.get();
            photo.setAvatar(true);
            photoRepository.save(photo);
        }
    }

    public void saveAvatar(MultipartFile avatar, Invertebrate invert) {
        if (avatar != null) {
            Cloudinary cloudinary = new Cloudinary();
            try {
                String file = "data:" + avatar.getContentType() + ";base64," +
                        DatatypeConverter.printBase64Binary(avatar.getBytes());
                Map uploadResults = cloudinary.uploader().upload(file, ObjectUtils.asMap(
                        "cloud_name", "mmaruszak",
                        "api_key", "816365741111569",
                        "api_secret", "i8K4Lh4avhtJXCTLA56jZSlJtFA",
                        "upload_preset", "xtmxos3s",
                        "folder", "inverts/" + invert.getUser().getId().toString()
                ));
                Photo Photo = new Photo(uploadResults.get("url").toString(),
                        uploadResults.get("public_id").toString(), LocalDateTime.now(), invert, true);
                photoRepository.save(Photo);
            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "{\"message\":\"There was a problem with files upload\"}");
            }
        }
    }

    public Optional<Photo> findLatestByInvert(Invertebrate inverts) {
        return photoRepository.findTopByInvertebrateOrderByAddedDesc(inverts);
    }
}
