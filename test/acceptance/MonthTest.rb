require 'test/unit'

class MonthTest < Test::Unit::TestCase
    def setup
        $driver.get($appli_url + '/clear')
        $driver.get($appli_url + '/month/205001')
    end

    def test_add_task
        add_task('some task')
        check_persisted {
            assert_equal('some task', $driver.find_element(:css, '.task .task-name').text, 'task not created')
            refute($driver.find_element(:css, '.task input[type="checkbox"]').attribute('checked'), 'task created in done state')
        }
    end

    def test_check_task
        add_task('some task')
        $driver.find_element(:css, '.task input[type="checkbox"]').click
        check_persisted { assert($driver.find_element(:css, '.task input[type="checkbox"]').attribute('checked'), 'done state not saved') }
        refute($driver.find_element(:css, '.task .edit-item-button').displayed?)
        refute($driver.find_element(:css, '.task .cancel-item-button').displayed?)
        refute($driver.find_element(:css, '.task .move-item-button').displayed?)
    end

    def test_cancel_task
        add_task('some task')
        $driver.find_element(:css, '.task .cancel-item-button').click
        check_persisted { assert_equal('cancelled', $driver.find_element(:css, '.task').attribute('data-status'), 'task not correctly cancelled') }
    end

    def test_delete_task
        add_task('some task')
        $driver.find_element(:css, '.task .cancel-item-button').click
        $driver.find_element(:css, '.task .delete-item-button').click
        refute_empty($driver.find_elements(:css, '.task'))
        $driver.find_element(:css, '.bootbox-confirm .btn-primary').click
        check_persisted { give_time { assert_empty($driver.find_elements(:css, '.task'), 'task not correctly deleted') } }
    end

    def test_change_month
        $driver.get($appli_url + '/month/205504')
        assert_equal('avril 2055', $driver.find_element(:css, '#displayed-month').text, 'month not rightly displayed')
        add_task('some task')
        $driver.find_element(:css, '#previous-month').click
        assert_equal('mars 2055', $driver.find_element(:css, '#displayed-month').text, 'month not rightly displayed')
        assert_empty($driver.find_elements(:css, '.task'), 'task found in wrong month')
        $driver.find_element(:css, '#next-month').click
        assert_equal('some task', $driver.find_element(:css, '.task .task-name').text, 'task not saved in month')
    end

    def test_move_task
        add_task('some task')
        $driver.find_element(:css, '.task .move-item-button').click
        $driver.find_element(:css, 'a.move-button[data-destination-id="205002"]').click
        assert_equal('moved', $driver.find_element(:css, '.task').attribute('data-status'), 'task not moved')
        $driver.find_element(:css, '#next-month').click
        assert_equal('some task', $driver.find_element(:css, '.task .task-name').text, 'new task not created')
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
