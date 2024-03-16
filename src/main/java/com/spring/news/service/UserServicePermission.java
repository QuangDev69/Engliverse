//@Service
//public class PermissionService {
//
//    @Autowired
//    private RolePermissionRepository rolePermissionRepository;
//
//    //phương thức này sẽ kiểm tra xem người dùng có quyền truy cập vào 1 URL nào đó không.
//    public boolean hasPermission(Long userId, String url) {
//        Long roleId = getRoleIdByUserId(userId);
//
//        List<RolePermission> rolePermissions = rolePermissionRepository.findByRoleId(roleId);
//
//        // Duyêt qua RolePermission của người dùng
//        for (RolePermission rp : rolePermissions) {
//
//            //Kiểm tra UrlAccess có trùng với URL mà người dùng truy cập không
//            if (rp.getPermission().getUrlAccess().equals(url)) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//
//    private Long getRoleIdByUserId(Long userId) {
//        return ...;
//    }
//}
//
//@Service
//public class CustomAuthenticationService {
//
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    public Authentication authenticate(String username, String password) {
//        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        return authentication;
//    }
//}
//@Component
//public class ApiSecurityInterceptor extends HandlerInterceptorAdapter {
//
//    @Autowired
//    private PermissionService permissionService;
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        // Get authentication information from SecurityContext
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication == null || !authentication.isAuthenticated()) {
//            // User is not authenticated
//            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "You must be logged in..");
//            return false;
//        }
//        // Check access to URL
//        String requestUrl = request.getRequestURI();
//        Long userId = ((MyUserDetails) authentication.getPrincipal()).getUserId();
//        boolean hasPermission = permissionService.hasPermission(userId, requestUrl);
//
//        if (!hasPermission) {
//            // User does not have access rights
//            response.sendError(HttpServletResponse.SC_FORBIDDEN, "You do not have permission to access this resource.");
//            return false;
//        }
//        return true;
//    }
//}
//
//@Service
//public class EncryptionService {
//    private final AES256TextEncryptor textEncryptor;
//    public EncryptionService() {
//        this.textEncryptor = new AES256TextEncryptor();
//        textEncryptor.setPassword("mysecretkey");
//    }
//    public String encrypt(String data) {
//        return textEncryptor.encrypt(data);
//    }
//    public String decrypt(String encryptedData) {
//        return textEncryptor.decrypt(encryptedData);
//    }
//}
//
//
//@Service
//public class SessionService {
//
//    public String getCurrentUsername() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication == null || !authentication.isAuthenticated()) {
//            return null;
//        }
//        return ((User) authentication.getPrincipal()).getUsername();
//    }
//
//    public void logout(HttpServletRequest request, HttpServletResponse response) {
//        new SecurityContextLogoutHandler().logout(request, response, null);
//    }
//}