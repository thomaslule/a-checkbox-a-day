require 'test/unit'

class MonthTest < Test::Unit::TestCase
    def setup
        $driver.get($appli_url + '/clear')
        $driver.get($appli_url + '/month')
    end

    def test_add_task
        add_task('some task')
        check_persisted {
            assert_equal('some task', $driver.find_element(:css, '.task .task-name').text)
            refute($driver.find_element(:css, '.task input[type="checkbox"]').attribute('checked'))
        }
    end

    def test_check_task
        add_task('some task')
        $driver.find_element(:css, '.task input[type="checkbox"]').click
        check_persisted { assert($driver.find_element(:css, '.task input[type="checkbox"]').attribute('checked')) }
    end

    def test_cancel_task
        add_task('some task')
        $driver.find_element(:css, '.task .cancel-item-button').click
        check_persisted { assert_equal('cancelled', $driver.find_element(:css, '.task').attribute('data-status')) }
    end

    def test_delete_task
        add_task('some task')
        $driver.find_element(:css, '.task .cancel-item-button').click
        $driver.find_element(:css, '.task .delete-item-button').click
        refute_empty($driver.find_elements(:css, '.task'))
        $driver.find_element(:css, '.bootbox-confirm .btn-primary').click
        wait = Selenium::WebDriver::Wait.new(:timeout => 2)
        check_persisted { give_time { assert_empty($driver.find_elements(:css, '.task')) } }
    end

    def add_task(name)
        input = $driver.find_element(:css, '#new-item input[name="name"]')
        input.send_keys(name)
        input.submit
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
