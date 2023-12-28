package pl.dockerguardimage.data.functionality.notification.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum NotificationType {

    SCAN_COMPLETED("common.scanCompleted="),
    COMMENT("common.comment"),
    FILE_ACCESS_ADD_READ("common.addAccessRead"),
    FILE_ACCESS_ADD_WRITE("common.addAccessWrite"),
    FILE_ACCESS_REMOVE("common.removeAccess"),
    FILE_ACCESS_UPDATE_READ("common.moveAccessRead"),
    FILE_ACCESS_UPDATE_WRITE("common.moveAccessWrite");

    private final String message;

}
