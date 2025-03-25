package dat.enums;

import io.javalin.security.RouteRole;

public enum Roles implements RouteRole
{
    TEACHER_READ,
    TEACHER_WRITE,
    USER_READ,
    USER_WRITE,
    ADMIN,
    CONTRIBUTOR,
    ANYONE,
}