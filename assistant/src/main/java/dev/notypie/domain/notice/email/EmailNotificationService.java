package dev.notypie.domain.notice.email;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("email")
@Service
public class EmailNotificationService {
}
