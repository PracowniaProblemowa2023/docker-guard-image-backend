package pl.dockerguardimage.core.functionality.notification.service;

import pl.dockerguardimage.data.functionality.comment.domain.Comment;
import pl.dockerguardimage.data.functionality.fileaccess.domain.FileAccess;
import pl.dockerguardimage.data.functionality.notification.domain.Notification;
import pl.dockerguardimage.data.functionality.user.domain.User;

public interface NotificationService {
    Notification addAccessType(User author, User givenTo, FileAccess fileAccess);

    Notification removedAccessType(User author, User givenTo, FileAccess fileAccess);

    Notification changedAccessType(User author, User givenTo, FileAccess fileAccess);

    Notification addComment(Comment comment);
}
