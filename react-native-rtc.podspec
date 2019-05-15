Pod::Spec.new do |s|
    s.name         = "react-native-rtc"
    s.version      = "1.0.6"
    s.platform     = :ios, "8.0"
    s.summary      = "react-native-rtc"
    s.homepage     = "https://github.com/arthasAndDk/react-native-rtc"
    s.license      = "MIT"
    s.author       = { "arthas" => "15215604969@163.com" }
    s.source       = { :git => "https://github.com/arthasAndDk/react-native-rtc.git", :tag => s.version }
    s.source_files = "ios/**/*.{h,m}"
    s.requires_arc = true
end
