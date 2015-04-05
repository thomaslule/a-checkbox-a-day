require 'test/unit'

class AcadTest < Test::Unit::TestCase
    def assert_has_class(className, element, message)
        assert(element.attribute('class').split(/\s+/).include?(className), message)
    end

    def check_persisted
        yield
        $driver.navigate.refresh
        yield
    end

    def give_time
        wait = Selenium::WebDriver::Wait.new(:timeout => 2)
        e = nil
        begin
            wait.until {
                success = nil
                begin
                    yield
                    success = true
                rescue Exception => e
                    success = false
                end
                success
            }
        rescue Exception => f
            raise e
        end
    end
end
